package studio.bz_soft.newsfeeder.ui.main.detailednews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detailed_news.view.*
import kotlinx.coroutines.CompletableDeferred
import org.koin.android.ext.android.get
import studio.bz_soft.mvilibrary.mvi.MVIFragment
import studio.bz_soft.newsfeeder.R
import studio.bz_soft.newsfeeder.data.models.Article
import studio.bz_soft.newsfeeder.root.BackPressedInterface

class DetailedNewsFragment : MVIFragment(), BackPressedInterface {

    private val news = CompletableDeferred<Article>()

    private val controller: DetailedNewsController by registered(
            { v, r -> render(v, r)},
            { v, a -> action(v, a)}
    ) { DetailedNewsController(get(), get(), news) }

    private fun action(v: View, a: DetailedNewsAction) {

    }

    private fun render(v: View, r: DetailedNewsRender) {
        return when (r) {
            is DetailedNewsRender.RenderArticle -> renderArticle(v, r.news)
        }
    }

    private fun renderArticle(v: View, news: Article) {
        v.apply {
            Glide.with(v).load(news.image).into(ivNews)
            tvDate.text = news.dateOfPublish
            tvTitle.text = news.title
            tvContent.text = news.content
        }
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