package com.example.projectandroidapp_findingroom

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firestore.RoomViewModel
import com.example.projectandroidapp_findingroom.navigation.NavItem
import com.example.projectandroidapp_findingroom.pages.AccountScreen
import com.example.projectandroidapp_findingroom.pages.ExtensionScreen
import com.example.projectandroidapp_findingroom.pages.HomeScreen
import com.example.projectandroidapp_findingroom.pages.NotificationScreen
import com.example.projectandroidapp_findingroom.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel, roomViewModel: RoomViewModel) {
    val navItemList = listOf(
        NavItem("Trang chủ", painterResource(R.drawable.baseline_home_filled_24), 0),
        NavItem("Công cụ", painterResource(R.drawable.baseline_extension_24), 0),
        NavItem("Thông báo", painterResource(R.drawable.baseline_notifications_24), 3), // giả sử có 3 thông báo
        NavItem("Tài khoản", painterResource(R.drawable.baseline_person_24), 0),
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(containerColor = colorResource(R.color.main_color)) {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = {
                            if (navItem.badgeCount > 0) {
                                BadgedBox(badge = {
                                    Badge {
                                        Text(text = navItem.badgeCount.toString())
                                    }
                                }) {
                                    Icon(
                                        painter = navItem.icon,
                                        contentDescription = navItem.label,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            } else {
                                Icon(
                                    painter = navItem.icon,
                                    contentDescription = navItem.label,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        },
                        label = {
                            Text(
                                text = navItem.label,
                                maxLines = 1
                            )
                        },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = colorResource(R.color.white),
                            selectedTextColor = colorResource(R.color.white),
                            unselectedIconColor = colorResource(R.color.black), // bạn cần thêm màu này vào `colors.xml`
                            unselectedTextColor = colorResource(R.color.black),
                            indicatorColor = colorResource(R.color.main_color)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            roomViewModel = roomViewModel,
            selectedIndex = selectedIndex,
            navController = navController,
            authViewModel = authViewModel,
        )
    }
}


@Composable
fun ContentScreen(modifier: Modifier = Modifier,roomViewModel: RoomViewModel, selectedIndex : Int, navController: NavController, authViewModel: AuthViewModel) {
    when(selectedIndex){
        0-> HomeScreen(roomViewModel, navController)
        1-> ExtensionScreen(navController)
        2-> NotificationScreen(navController)
        3-> AccountScreen(navController, authViewModel)
    }
}
