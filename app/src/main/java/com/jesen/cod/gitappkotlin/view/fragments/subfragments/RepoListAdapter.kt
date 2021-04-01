package com.jesen.cod.gitappkotlin.view.fragments.subfragments

import android.support.v7.widget.RecyclerView
import android.view.View
import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.entities.Repository
import com.jesen.cod.gitappkotlin.utils.loadWithGlide
import com.jesen.cod.gitappkotlin.utils.simpleStartActivity
import com.jesen.cod.gitappkotlin.utils.toKilo
import com.jesen.cod.gitappkotlin.view.RepoDetailActivity
import com.jesen.cod.gitappkotlin.view.common.CommonListAdapter
import com.jesen.cod.gitappkotlin.view.startRepoDetailActivity
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoListAdapter : CommonListAdapter<Repository>(R.layout.item_repo) {

    override fun onBindData(viewHolder: RecyclerView.ViewHolder, repository: Repository) {
        viewHolder.itemView.apply {
            avatarView.loadWithGlide(repository.owner.avatar_url, repository.owner.login.first())
            repoNameView.text = repository.name
            descriptionView.text = repository.description
            langView.text = repository.language ?: "Unknown"
            starView.text = repository.stargazers_count.toKilo()
            forkView.text = repository.forks_count.toKilo()
        }
    }

    override fun onItemClicked(itemView: View, item: Repository) {
        itemView.startRepoDetailActivity(item)
        /*simpleStartActivity<RepoDetailActivity>(itemView.context){
            putExtra("repository", item)
        }*/
    }


}