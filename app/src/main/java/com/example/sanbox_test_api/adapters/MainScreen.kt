package com.example.sanbox_test_api.adapters

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.sanbox_test_api.R
import com.example.sanbox_test_api.fragments.HomeFragment
import com.example.sanbox_test_api.fragments.PaymentsFragment
import com.example.sanbox_test_api.fragments.TransactionsFragment
import com.example.sanbox_test_api.fragments.UserFragment

/**
 * @enum Screens available for display in the main screen, with their respective titles,
 * icons, and menu item IDs and fragments.
 */
@RequiresApi(Build.VERSION_CODES.O)
enum class MainScreen(@IdRes val menuItemId: Int,
                      @DrawableRes val menuItemIconId: Int,
                      @StringRes val titleStringId: Int,
                      val fragment: Fragment
) {
    HOME(R.id.bottom_navigation_menu_home,
        R.drawable.ic_home_black_24dp, R.string.home,
        HomeFragment()
    ),
    TRANSACTIONS(R.id.bottom_navigation_menu_transactions,
        R.drawable.ic_payment_black_24dp, R.string.home,
        TransactionsFragment()
    ),

    PAYMENTS(R.id.bottom_navigation_menu_payments,
        R.drawable.ic_compare_arrows_black_24dp, R.string.payment,
        PaymentsFragment()
    ),
    USER(R.id.bottom_navigation_menu_user,
        R.drawable.ic_person_black_24dp, R.string.user,
        UserFragment()
    ),

}

/**
 * Get the correct menu
 * @param menuItemId
 **/
fun getMainScreenForMenuItem(menuItemId: Int): MainScreen? {
    for (mainScreen in MainScreen.values()) {
        if (mainScreen.menuItemId == menuItemId) {
            return mainScreen
        }
    }
    return null
}