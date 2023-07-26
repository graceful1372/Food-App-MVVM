package com.graceful1372.foodappnouri.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.graceful1372.foodappnouri.R
import com.graceful1372.foodappnouri.databinding.FragmentFoodsFavoriteBinding
import com.graceful1372.foodappnouri.databinding.FragmentFoodsListBinding
import com.graceful1372.foodappnouri.utlis.isVisible
import com.graceful1372.foodappnouri.utlis.setupRecyclerview
import com.graceful1372.foodappnouri.viewmodel.FoodsFavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FoodsFavoriteFragment : Fragment() {
    //Binding
    private var _binding: FragmentFoodsFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    //Other
    private  val viewModel:FoodsFavoriteViewModel by viewModels ()

    //Other
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFoodsFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //InitViews
        binding.apply {
            viewModel.loadFavorites()
            viewModel.favoriteListLiveData.observe(viewLifecycleOwner){
                if (it.isEmpty) {
                    emptyLay.isVisible(true , favoriteList)
                    statusLay.disImg.setImageResource(R.drawable.box)
                    statusLay.disTxt.text= getString(R.string.emptyList)

                }else{
                    emptyLay.isVisible(false , favoriteList)
                    favoriteAdapter.setData(it.data!!)
                    favoriteList.setupRecyclerview(LinearLayoutManager(requireContext()), favoriteAdapter)

                    favoriteAdapter.setOnItemClickListener {food->
                        val direction = FoodsFavoriteFragmentDirections.actionFoodsListFragmentToFoodDetailFragment(food.id)
                        findNavController().navigate(direction)
                    }

                }
            }


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}