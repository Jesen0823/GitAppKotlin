package com.jesen.cod.gitappkotlin.view.fragments.subfragments

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import com.jesen.cod.common.log.logger
import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.entities.User2
import com.jesen.cod.gitappkotlin.utils.loadWithGlide
import com.jesen.cod.gitappkotlin.view.common.CommonListAdapter
import kotlinx.android.synthetic.main.item_repo.view.avatarView
import kotlinx.android.synthetic.main.item_user.view.*
import okhttp3.logging.HttpLoggingInterceptor

class PeopleListAdapter : CommonListAdapter<User2>(R.layout.item_user) {

    override fun onItemClicked(itemView: View, item: User2) {
        logger.debug("PeopleListAdapter, onItemClicked")
    }

    override fun onBindData(viewHolder: ViewHolder, user: User2) {
        viewHolder.itemView.apply {
            avatarView.loadWithGlide(user.avatar_url, user.login.first())
            nameView.text = user.login
        }
    }
}