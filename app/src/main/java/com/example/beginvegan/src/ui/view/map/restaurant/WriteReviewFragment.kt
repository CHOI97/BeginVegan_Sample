package com.example.beginvegan.src.ui.view.map.restaurant

import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import com.example.beginvegan.R
import com.example.beginvegan.config.BaseFragment
import com.example.beginvegan.databinding.FragmentWriteReviewBinding
import com.example.beginvegan.src.data.model.review.ReviewInterface
import com.example.beginvegan.src.data.model.review.ReviewListResponse
import com.example.beginvegan.src.data.model.review.ReviewService
import com.example.beginvegan.src.data.model.review.WriteReviewResponse

class WriteReviewFragment : BaseFragment<FragmentWriteReviewBinding>(
    FragmentWriteReviewBinding::bind,
    R.layout.fragment_write_review
), ReviewInterface {
    private var restaurantId = 0
    override fun init() {

        showLoadingDialog(requireContext())// callback Fragment Review
        parentFragmentManager.setFragmentResultListener(
            "review",
            viewLifecycleOwner
        ) { _, bundle ->
            restaurantId = bundle.getInt("review")
            Log.d("WriteReviewFragment", restaurantId.toString())
        }
        binding.bSaveReview.setOnClickListener {

            showLoadingDialog(requireContext())
            val result = binding.etWriteReview.text.toString()
            ReviewService(this).tryPostWriteReview(restaurantId,result)
            dismissLoadingDialog()
        }
        dismissLoadingDialog()
    }

    override fun onPostWriteReviewSuccess(response: WriteReviewResponse) {
        Toast.makeText(requireContext(),response.information.message,Toast.LENGTH_SHORT).show()
        dismissLoadingDialog()
        parentFragmentManager.setFragmentResult("return",bundleOf("return" to restaurantId))
        parentFragmentManager.popBackStack()
    }

    override fun onPostWriteReviewFailure(message: String) {
        Toast.makeText(requireContext(),"리뷰작성 실패",Toast.LENGTH_SHORT).show()
        Log.d("onPostWriteReviewFailure",message)
        dismissLoadingDialog()
    }

    override fun onGetReviewListSuccess(response: ReviewListResponse) {}
    override fun onGetReviewListAddSuccess(response: ReviewListResponse) {
    }

    override fun onGetReviewListFailure(message: String) {}
}