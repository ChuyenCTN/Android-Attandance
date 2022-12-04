package com.hust.attandance.ui.classes

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.hust.attandance.databinding.FragmentDetailClassesBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import com.hust.attandance.utils.extensions.safeNavigateUp
import com.hust.attandance.utils.extensions.setSafeOnClickListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class DetailClassesFragment :
    BaseViewBindingFragment<FragmentDetailClassesBinding, DetailClassesViewModel>(
        FragmentDetailClassesBinding::inflate
    ) {
    override val viewModel by sharedViewModel<DetailClassesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            val managerAdapter = DetailClassManagerAdapter(this@DetailClassesFragment)
            pagerClass.adapter = managerAdapter
            pagerClass.isUserInputEnabled = false
            for (position in 0 until managerAdapter.itemCount) {
                val tab = tabClass.newTab()
                val title = when (position) {
                    DetailClassManagerAdapter.STUDENT_TAB_INDEX -> "Sinh viên"
                    else -> "Lịch học"
                }
                tab.customView = tabClass.inflateTabView(title)
                tabClass.addTab(tab, false)

                tabClass.addOnTabSelectedListener(onTabSelected)
            }
            btnBack.setSafeOnClickListener {
                findNavController().safeNavigateUp()
            }
        }
    }

    private val onTabSelected = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            Timber.d("onTabSelected: $tab")
            viewBinding.apply {
                tab?.position?.let { position ->
                    tabClass.updateTabSelected(position)
                    pagerClass.setCurrentItem(position, true)
                }
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabReselected(tab: TabLayout.Tab?) {}
    }

    private val onPageChanged = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            Timber.d("onPageSelected: $position")
            super.onPageSelected(position)
            viewBinding.tabClass.selectTab(
                viewBinding.tabClass.getTabAt(position), true
            )
        }
    }

    override fun onDestroyView() {
        viewBinding.tabClass.removeOnTabSelectedListener(onTabSelected)
        viewBinding.pagerClass.adapter = null
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        viewBinding.pagerClass.registerOnPageChangeCallback(onPageChanged)
    }

    override fun onStop() {
        super.onStop()
        viewBinding.pagerClass.unregisterOnPageChangeCallback(onPageChanged)
    }

}

//    Mục tiêu:
//Trở thành lập trình viên mobile chuyên nghiệp
//Ngắn hạn: Lập trình viên android chuyên nghiệp
//Dài hạn: học và làm thêm iOS
//
//
//Cần làm:
//- Cần học và thực hành những yêu cầu còn thiếu trong career Track vào app, tăng level của bản thân, giải quyết được những task khó (hiểu, áp dụng - clean code, SOLID, Design Pattern, coroutine, livedata, animation, cleanArc)
//- Làm các task lớn hơn để nắm được nghiệp vụ, trau dồi thêm kiến thức chuyên ngành,  áp dụng các công nghệ mới vào app, optimize code
//
//Lập trình viên Android chuyên nghiệp là:
//- Cần nắm vững ít nhất 1 ngôn ngữ lập trình (JAVA, KOTLIN) để tận dụng hết các điểm mạnh của ngôn ngữ lập trình đó, giải những bài toán khó
//KOTLIN kế thừa từ JAVA, mạnh mẽ, ngắn gọn hơn JAVA
//- Nắm được các thành phần của Android (Android Component) để biết đucợ với từng bài toán sẽ sử dụng thành phần nào của android, tại sao lại dùng nó và nó tối ưu hơn những thành phần khác ở điểm nào
//- Nắm được kiến trúc ứng dụng (Architecture)
//- Hiểu và nắm được các logic nghiệp vụ của ứng dụng (Mỗi lần refine biết được độ khó của task này như nào, cần chuẩn bị những gì, api cần những thông tin gì…)
//- Lời nói đi đôi với hành động, đã nói là phải làm, đưa ra end date thì phải bàn giao đúng thời hạn
//
//
//Team chuyên nghiệp là gì? Cần làm gì để trở thành 1 team chuyên nghiệp
//Là team phải có các thành viên chuyên nghiệp, làm việc hiệu quả, cả team có mục tiêu chung
//
//Đặt ra mục tiêu chung của cả team để cùng nhau cố gắng, phấn đấu hướng đến nó
//Cả nhóm cần hiểu rõ mục tiêu và cam kết phấn đấu vì mục tiêu đó. Có định hướng và thống nhất rõ ràng về sứ mệnh và mục đích là điều rất quan trọng để team làm việc một cách hiệu quả. Nếu cả nhóm đều có kỳ vọng rõ ràng về công việc, mục tiêu, trách nhiệm và kết quả, làm việc nhóm sẽ trở nên suôn sẻ hơn.
//
//Con người (Thành viên trong team phải chuyên nghiệp), các thành viên trong team cần nhận thức được vai trò, trách nhiệm của mình đối với team, mọi người trong team có thể linh hoạt xử lý các task không phải là điểm mạnh của mình để tăng sự thử thách trong công việc, có thể support các task của thành viên khác khi họ có việc bận
//Mọi người trong team tôn trọng và tin tưởng nhau -> giảm thiểu xung đột, tăng sự đoàn kết cùng hướng đến mục tiêu chung
