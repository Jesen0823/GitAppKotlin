package com.jesen.cod.gitappkotlin.view.fragments.subfragments

import android.support.v7.widget.RecyclerView
import android.view.View
import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.entities.Issue
import com.jesen.cod.gitappkotlin.utils.htmlText
import com.jesen.cod.gitappkotlin.utils.timeToDate
import com.jesen.cod.gitappkotlin.utils.view
import com.jesen.cod.gitappkotlin.view.common.CommonListAdapter
import kotlinx.android.synthetic.main.item_issue.view.*
import org.jetbrains.anko.imageResource

class IssueListAdapter : CommonListAdapter<Issue>(R.layout.item_issue) {

    override fun onBindData(viewHolder: RecyclerView.ViewHolder, issue: Issue) {
        viewHolder.itemView.apply {
            iconView.imageResource =
                if (issue.state == "open") R.drawable.ic_issue_open else R.drawable.ic_issue_closed
            timeView.text = issue.title
            timeView.text = timeToDate(issue.created_at).view()
            bodyView.htmlText = issue.body_html
            commentCount.text = issue.comments.toString()
        }
    }

    override fun onItemClicked(itemView: View, item: Issue) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
















