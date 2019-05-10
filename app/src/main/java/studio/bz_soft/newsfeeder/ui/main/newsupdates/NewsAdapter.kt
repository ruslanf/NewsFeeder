package studio.bz_soft.newsfeeder.ui.main.newsupdates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.news_cell.view.*
import studio.bz_soft.newsfeeder.R
import studio.bz_soft.newsfeeder.data.models.Article
import studio.bz_soft.newsfeeder.root.delegated.AdapterDelegateInterface
import studio.bz_soft.newsfeeder.root.delegated.BaseHolder

sealed class NewsElements {
    data class NewsItem(val news: Article) : NewsElements()
}

class NewsItemHolder(v: View, val onClick: (Article) -> Unit) : BaseHolder<NewsElements>(v) {

    override fun bindModel(item: NewsElements) {
        super.bindModel(item)
        when (item) {
            is NewsElements.NewsItem -> itemView.apply {
                Glide.with(itemView)
                    .load(item.news.image)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivNews)
                tvNewsTitle.text = item.news.title
                tvNewsDate.text = item.news.dateOfPublish
                setOnClickListener { onClick(item.news) }
            }
        }
    }
}

class NewsItemDelegate(private val onClick: (Article) -> Unit) : AdapterDelegateInterface<NewsElements> {

    override fun isForViewType(items: List<NewsElements>, position: Int): Boolean {
        return items[position] is NewsElements.NewsItem
    }

    override fun createViewHolder(parent: ViewGroup): BaseHolder<NewsElements> {
        return NewsItemHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.news_cell, parent, false),
                onClick)
    }

    override fun bindViewHolder(items: List<NewsElements>, position: Int, holder: BaseHolder<NewsElements>) {
        holder.bindModel(items[position])
    }
}