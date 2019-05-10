package studio.bz_soft.newsfeeder.ui.main.detailednews

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detailed_news.view.*
import kotlinx.coroutines.CompletableDeferred
import org.koin.android.ext.android.get
import studio.bz_soft.mvilibrary.mvi.MVIFragment
import studio.bz_soft.newsfeeder.R
import studio.bz_soft.newsfeeder.data.models.Article
import studio.bz_soft.newsfeeder.root.BackPressedInterface
import studio.bz_soft.newsfeeder.root.common.transformDate

class DetailedNewsFragment : MVIFragment(), BackPressedInterface {

    private val news = CompletableDeferred<Article>()

    private val controller: DetailedNewsController by registered(
            { v, r -> render(v, r)}
    ) { DetailedNewsController(get(), news) }

    private fun render(v: View, r: DetailedNewsRender) {
        return when (r) {
            is DetailedNewsRender.RenderArticle -> renderArticle(v, r.news)
        }
    }

    private fun renderArticle(v: View, news: Article) {
        v.apply {
            Glide.with(v)
                .load(news.image)
                .into(ivNews)
            tvAuthor.text = news.author
            tvDate.text = transformDate(news.dateOfPublish)
            tvTitle.text = news.title
            tvContent.apply {
                text = news.content
                setOnClickListener { onLinkPressed(news.url) }
            }
        }
    }

    private fun onLinkPressed(url: String) {
        val customTabs = CustomTabsIntent.Builder().build()
        customTabs.launchUrl(context, Uri.parse(url))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        news.complete(arguments?.getParcelable("news") ?: Article())
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detailed_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            ivBackButton.setOnClickListener { controller.sendIntent(DetailedNewsIntent.Back) }
        }
    }

    override fun onBackPressed(): Boolean {
        controller.sendIntent(DetailedNewsIntent.Back)
        return true
    }

    companion object {
        fun instance(news: Article) : DetailedNewsFragment = DetailedNewsFragment().apply {
            arguments = Bundle().apply {
                putParcelable("news", news)
            }
        }
    }
}