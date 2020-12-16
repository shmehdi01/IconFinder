package syed.iconfinder.ui.search

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.disposables.Disposable
import syed.iconfinder.base.BaseFragment
import syed.iconfinder.databinding.FragmentSearchBinding
import syed.iconfinder.factory.ViewModelProviderFactory
import syed.iconfinder.ui.FullScreenViewActivity
import syed.iconfinder.ui.icons.IconAdapter
import syed.iconfinder.utils.*
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private var disposable: Disposable? = null
    private lateinit var iconAdapter: IconAdapter
    private lateinit var paging: RecyclerPagination

    private var searchQuery = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
    }

    override fun getViewModel(): Class<SearchViewModel> = SearchViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)

    override fun getViewModelFactory(): ViewModelProviderFactory = viewModelProviderFactory

    override fun observeData() {
        super.observeData()

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            binding.progressCircular.show(it)
        })

        viewModel.iconsData.observe(viewLifecycleOwner, Observer {
            if (this::paging.isInitialized) {
                paging.setShouldLoadMore(viewModel.hasMoreData)
            }
            iconAdapter.submitList(it)


            binding.tvNoData.show(!viewModel.isCleared && it.isEmpty())

        })

    }

    private fun setData() {
        disposable = binding.etSearch.fromEditText(
            bindClear = binding.ivClear,
            onClear = { viewModel.clearSearch() })
            .debounce(500, TimeUnit.MILLISECONDS)
            .filter {
                it.isNotEmpty()
            }
            .subscribe {
                searchQuery = it
                viewModel.searchIcon(it,initialCount = 20)
            }

        binding.rcvIcon
            .apply {
                layoutManager = GridLayoutManager(context, 3).also {
                    setPaging(it) { p ->
                        paging = p
                        viewModel.searchIcon(searchQuery)
                    }
                }

                adapter = IconAdapter { icon, iv ->

                    val activityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            requireActivity(),
                            iv,
                            "icon"
                        )
                    val intent = Intent(
                        requireContext(),
                        FullScreenViewActivity::class.java
                    )
                    intent.putParcelableArrayListExtra(
                        Constants.KEY_RASTER_SIZE,
                        icon.raster_sizes as ArrayList<out Parcelable>
                    )
                    intent.putExtra(Constants.KEY_CATEGORY, "")

                    startActivity(intent, activityOptionsCompat.toBundle())

                }.also { iconAdapter = it }
            }
    }

}