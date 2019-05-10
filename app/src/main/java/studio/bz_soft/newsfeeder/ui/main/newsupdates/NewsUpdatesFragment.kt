package studio.bz_soft.newsfeeder.ui.main.newsupdates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_news_updates.view.*
import org.koin.android.ext.android.get
import studio.bz_soft.mvilibrary.mvi.MVIFragment
import studio.bz_soft.newsfeeder.R
import studio.bz_soft.newsfeeder.data.models.Article
import studio.bz_soft.newsfeeder.root.BackPressedInterface
import studio.bz_soft.newsfeeder.root.delegated.DelegateAdapter

class NewsUpdatesFragment : MVIFragment(), BackPressedInterface {

    private val controller: NewsUpdatesController by registered(
            { v, r -> render(v, r) }
    ) { NewsUpdatesController(get(), get()) }

    private val dAdapter = DelegateAdapter(NewsItemDelegate { news ->
        controller.sendIntent(NewsUpdatesIntent.SelectNews(news))
    })

    private fun render(v: View, r: NewsUpdatesRender) {
        return when (r) {
            is NewsUpdatesRender.ListNewsRender -> renderNews(r.news)
            is NewsUpdatesRender.ProgressBarRender -> renderProgressBar(v, r.isShown)
            is NewsUpdatesRender.SwipeRefreshRender -> renderSwipeRefresh(v, r.refreshing)
        }
    }

    private fun renderSwipeRefresh(v: View, refreshing: RefreshStatus) {
        v.swipeRefresh.isRefreshing = when (refreshing) {
            RefreshStatus.REFRESHED -> false
            else -> true
        }
    }

    private fun renderNews(news: List<Article>) {
        dAdapter.apply {
            items.clear()
            items.addAll(news.map { NewsElements.NewsItem(it) })
            notifyDataSetChanged()
        }
    }

    private fun renderProgressBar(v: View, isShown: Boolean) {
        v.apply {
            progressBar.visibility = when (isShown) {
                true -> View.VISIBLE
                false -> View.GONE
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_updates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            swipeRefresh.setOnRefreshListener { controller.sendIntent(NewsUpdatesIntent.Refresh) }

            recyclerViewNews.apply {
                adapter = dAdapter
                layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
            }
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    companion object {
        fun instance(): NewsUpdatesFragment = NewsUpdatesFragment()
    }
}