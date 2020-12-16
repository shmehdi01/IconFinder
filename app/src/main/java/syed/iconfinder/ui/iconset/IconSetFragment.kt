package syed.iconfinder.ui.iconset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import syed.iconfinder.base.BaseFragment
import syed.iconfinder.databinding.FragmentIconSetBinding
import syed.iconfinder.factory.ViewModelProviderFactory
import syed.iconfinder.ui.category.CategoryAdapter
import syed.iconfinder.ui.icons.IconsViewModel
import syed.iconfinder.ui.search.SearchViewModel
import syed.iconfinder.utils.RecyclerPagination
import syed.iconfinder.utils.setPaging
import syed.iconfinder.utils.show
import javax.inject.Inject

class IconSetFragment : BaseFragment<IconSetViewModel, FragmentIconSetBinding>() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var iconSetAdapter: IconSetAdapter
    private lateinit var category: String
    private lateinit var navController: NavController
    private lateinit var paging: RecyclerPagination

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category = IconSetFragmentArgs.fromBundle(requireArguments()).category
        navController = Navigation.findNavController(view)

        setToolbar()
        setData()

        viewModel.loadIconSet(category, initialCount = 20)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getViewModel(): Class<IconSetViewModel>  = IconSetViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIconSetBinding = FragmentIconSetBinding.inflate(inflater,container,false)

    override fun getViewModelFactory(): ViewModelProviderFactory = viewModelProviderFactory

    override fun observeData() {
        super.observeData()

        viewModel.iconSetData.observe(viewLifecycleOwner, Observer {
            if(this::paging.isInitialized) {
                paging.setShouldLoadMore(viewModel.hasMoreData)
            }
            iconSetAdapter.submitList(it)
            binding.tvNoData.show(it.isEmpty())
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            binding.progressCircular.show(it)
        })
    }

    private fun setToolbar() {
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                title = category.capitalize()
            }
        }
    }

    private fun setData() {
        binding.rcvIconSet.apply {
            layoutManager = LinearLayoutManager(context).also {
                setPaging(it) { paging ->
                    paging.setShouldLoadMore(viewModel.hasMoreData)
                    viewModel.loadIconSet(category)
                }
            }

            adapter = IconSetAdapter {
                IconSetFragmentDirections.actionIconSetFragmentToIconsFragment(it.iconSetId, it.name).apply {
                    navController.navigate(this)
                }
            }.also { iconSetAdapter = it }
        }
    }
}