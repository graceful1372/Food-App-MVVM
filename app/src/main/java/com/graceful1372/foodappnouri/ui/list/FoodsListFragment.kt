package com.graceful1372.foodappnouri.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.graceful1372.foodappnouri.R
import com.graceful1372.foodappnouri.databinding.FragmentFoodsListBinding
import com.graceful1372.foodappnouri.ui.list.adapters.CategoriesAdapter
import com.graceful1372.foodappnouri.ui.list.adapters.FoodsAdapter
import com.graceful1372.foodappnouri.utlis.CheckConnection
import com.graceful1372.foodappnouri.utlis.MyResponse
import com.graceful1372.foodappnouri.utlis.isVisible
import com.graceful1372.foodappnouri.utlis.setupListWithAdapter
import com.graceful1372.foodappnouri.utlis.setupRecyclerview
import com.graceful1372.foodappnouri.viewmodel.FoodListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FoodsListFragment : Fragment() {

    private var _binding: FragmentFoodsListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var categoryAdapter: CategoriesAdapter

    @Inject
    lateinit var foodAdapter: FoodsAdapter

    @Inject
    lateinit var connection: CheckConnection

    //Other
    private val viewModel: FoodListViewModel by viewModels()

    enum class PageState { EMPTY, NETWORK, NONE }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFoodsListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //InitViews
        binding.apply {

            //RandomFood
//            viewModel.loadFoodRandom()
            viewModel.randomFoodLiveData.observe(viewLifecycleOwner) {
                it[0].let { meal ->
                    headerImg.load(meal.strMealThumb) {
                        crossfade(true)
                        crossfade(500)
                    }
                }
            }
            //Filters
            viewModel.loadFilterList()
            viewModel.filterListLiveData.observe(viewLifecycleOwner) {
                filterSpinner.setupListWithAdapter(it) { letter ->
                    viewModel.loadFoodList(letter)

                }
            }
            //Categories
//            viewModel.loadCategoriesList()
            viewModel.categoriesListLiveData.observe(viewLifecycleOwner) {
                when (it.status) {
                    MyResponse.Status.LOADING -> {
                        homeCategoryLoading.isVisible(true, categoryList)
                    }

                    MyResponse.Status.SUCCESS -> {
                        homeCategoryLoading.isVisible(false, categoryList)
                        categoryAdapter.setData(it.data!!.categories)
                        categoryList.setupRecyclerview(
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            ), categoryAdapter
                        )

                    }

                    MyResponse.Status.ERROR -> {
                        homeCategoryLoading.isVisible(false, categoryList)

                    }
                }
            }
            categoryAdapter.setOnItemClickListener {
                viewModel.loadFoodByCategory(it.strCategory.toString())
            }
            //Foods
//            viewModel.loadFoodList("A")
            viewModel.foodsListLiveData.observe(viewLifecycleOwner) {
                when (it.status) {
                    MyResponse.Status.LOADING -> {
                        homeFoodsLoading.isVisible(true, foodsList)
                    }

                    MyResponse.Status.SUCCESS -> {

                        homeFoodsLoading.isVisible(false, foodsList)
                        if (it.data!!.meals != null) {
                            if (it.data.meals!!.isNotEmpty()) {
                                checkConnectionOrEmpty(false, PageState.NONE)
                                foodAdapter.setData(it.data.meals)
                                foodsList.setupRecyclerview(
                                    LinearLayoutManager(
                                        requireContext(),
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                    ), foodAdapter
                                )
                            }
                        }else{
                            checkConnectionOrEmpty(true, PageState.EMPTY)

                        }


                    }

                    MyResponse.Status.ERROR -> {
                        homeFoodsLoading.isVisible(false, foodsList)

                    }
                }

            }
            foodAdapter.setOnItemClickListener {
                val action = FoodsListFragmentDirections.actionFoodsListFragmentToFoodDetailFragment(it.idMeal!!.toInt())
                findNavController().navigate(action)
            }
            //Search
            searchEdt.addTextChangedListener {
                if (it.toString().length > 2) {
                    viewModel.loadFoodBySearch(it.toString())
                }
            }
            //Internet
            connection.observe(viewLifecycleOwner) {
                if (it) {
                    checkConnectionOrEmpty(false, PageState.NONE)
                } else {
                    checkConnectionOrEmpty(true, PageState.NETWORK)
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkConnectionOrEmpty(isShownError: Boolean, state: PageState) {
        binding.apply {
            if (isShownError) {
                homeDisLay.isVisible(true, homeContent)

                when (state) {
                    PageState.EMPTY -> {
                        disconnectLay.disImg.setImageResource(R.drawable.box)
                        disconnectLay.disTxt.text = getString(R.string.emptyList)
                    }

                    PageState.NETWORK -> {
                        disconnectLay.disImg.setImageResource(R.drawable.disconnect)
                        disconnectLay.disTxt.text = getString(R.string.checkInternet)
                    }

                    else -> {}
                }

            } else {
                homeDisLay.isVisible(false, homeContent)
            }
        }
    }
}