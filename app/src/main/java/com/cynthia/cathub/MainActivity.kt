package com.cynthia.cathub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cynthia.cathub.ui.theme.CathubTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CathubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppScaffold()
                }
            }
        }
    }
}

@Composable
fun AppScaffold() {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("首页", "囤货", "档案")

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Home, null)
                    }
                },
                title = {
                    Text("臭屁猫")
                }
            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.Blue,
                elevation = 3.dp
            ) {
                BottomNavigation {
                    items.forEachIndexed { index, item ->
                        BottomNavigationItem(
                            icon = {
                                when (index) {
                                    0 -> Icon(Icons.Filled.Home, contentDescription = null)
                                    1 -> Icon(Icons.Filled.Favorite, contentDescription = null)
                                    else -> Icon(Icons.Filled.Menu, contentDescription = null)
                                }
                            },
                            label = { Text(item) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index }
                        )
                    }
                }
            }
        },
        drawerContent = {
            AppDrawerContent(scaffoldState, scope)
        }
    ) {
        when (selectedItem) {
            0 -> {
                AppContent(item = items[selectedItem])
            }
            1 -> {

            }
            else -> {

            }
        }
    }
}


@Composable
fun AppContent(
    item: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("当前界面是 $item")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppDrawerContent(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    Box {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(35.dp)
                    .border(BorderStroke(1.dp, Color.Gray), CircleShape)
            )
            Spacer(Modifier.padding(horizontal = 5.dp))
            Text(
                text = "臭屁猫",
                color = Color.Black,
                style = MaterialTheme.typography.body2
            )
        }
    }

    ListItem(
        icon = {
            Icon(Icons.Filled.Email, null)
        },
        modifier = Modifier
            .clickable {

            }
    ) {
        Text("消息中心")
    }

    Box(
        modifier = Modifier.fillMaxHeight(),
        contentAlignment = Alignment.BottomStart
    ) {
        TextButton(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface),
        ) {
            Icon(Icons.Filled.Settings, null)
            Text("设置")
        }
    }

    BackHandler(enabled = scaffoldState.drawerState.isOpen) {
        scope.launch {
            scaffoldState.drawerState.close()
        }
    }
}