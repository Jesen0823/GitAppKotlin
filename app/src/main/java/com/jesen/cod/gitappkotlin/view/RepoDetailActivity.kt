package com.jesen.cod.gitappkotlin.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.widget.Switch
import com.bennyhuo.tieguanyin.annotations.ActivityBuilder
import com.bennyhuo.tieguanyin.annotations.Required
import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.entities.Repository
import com.jesen.cod.gitappkotlin.network.service.ActivityService
import com.jesen.cod.gitappkotlin.network.service.RepositoryService
import com.jesen.cod.gitappkotlin.utils.*
import com.jesen.cod.gitappkotlin.view.common.BaseDetailActivity
import com.jesen.cod.gitappkotlin.view.common.BaseDetailSwipeFinishableActivity
import com.jesen.cod.gitappkotlin.view.config.Themer
import kotlinx.android.synthetic.main.activity_repo_detail.*
import kotlinx.android.synthetic.main.app_bar_details.*
import retrofit2.Response
import rx.Subscriber

@ActivityBuilder
class RepoDetailActivity : BaseDetailActivity() {

    @Required
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        repository = intent.extras.getParcelable("repository")

        initDetails()
        reloadDetails()
    }

    private fun initDetails() {
        avatarView.loadWithGlide(repository.owner.avatar_url, repository.owner.login.first())
        collapsingToolbar.title = repository.name

        descriptionView.markdownText = getString(
            R.string.repo_description_template,
            repository.owner.login,
            repository.owner.html_url,
            repository.name,
            repository.html_url,
            repository.owner.login,
            repository.owner.html_url,
            timeToDate(repository.created_at).view()
        )

        bodyView.text = repository.description

        detailContainer.alpha = 0f

        stars.checkEvent = { isChecked ->
            if (isChecked) {
                ActivityService.unstar(repository.owner.login, repository.name).map { false }
            } else {
                ActivityService.star(repository.owner.login, repository.name).map { true }
            }.doOnNext { reloadDetails(true) }
        }

        watches.checkEvent = { isChecked ->
            if (isChecked) {
                ActivityService.unwatch(repository.owner.login, repository.name).map { false }
            } else {
                ActivityService.watch(repository.owner.login, repository.name).map { true }
            }.doOnNext { reloadDetails(true) }
        }

        ActivityService.isStarred(repository.owner.login, repository.name)
            .onErrorReturn {
                if (it is retrofit2.HttpException) {
                    it.response() as Response<Any>
                } else {
                    throw  it
                }
            }
            .subscribeIgnoreError {
                stars.isChecked = it.isSuccessful
            }
    }

    private fun reloadDetails(forceNetwork: Boolean = false) {
        RepositoryService.getRepository(repository.owner.login, repository.name, forceNetwork)
            .subscribe(object : Subscriber<Repository>() {
                override fun onStart() {
                    super.onStart()
                    loadingView.animate().alpha(1f).start()
                }

                override fun onNext(t: Repository) {
                    repository = t

                    owner.content = repository.owner.login
                    stars.content = repository.stargazers_count.toString()
                    watches.content = repository.watchers_count.toString()
                    forks.content = repository.forks_count.toString()

                    loadingView.animate().alpha(0f).start()
                    detailContainer.animate().alpha(1f).start()
                }

                override fun onCompleted() = Unit

                override fun onError(e: Throwable?) {
                    loadingView.animate().alpha(0f).start()
                    e?.printStackTrace()
                }
            })
    }
}