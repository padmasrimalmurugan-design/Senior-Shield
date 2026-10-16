package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.AppTab
import com.example.model.NavigationDestination
import com.example.ui.SeniorShieldViewModel
import com.example.ui.screens.CheckCallScreen
import com.example.ui.screens.CheckMessageScreen
import com.example.ui.screens.ClaimsScreen
import com.example.ui.screens.EmergencyHelpScreen
import com.example.ui.screens.HealthcareHomeScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.HospitalsDirectoryScreen
import com.example.ui.screens.LearnScamsScreen
import com.example.ui.screens.MoreScreen
import com.example.ui.screens.RewardsScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SignInScreen
import com.example.ui.screens.attemptAutoSignIn
import com.example.ui.theme.DarkPillButton
import com.example.ui.theme.GuardianNavy
import com.example.ui.theme.SeniorShieldTheme
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val credentialManager = CredentialManager.create(this)
        lifecycleScope.launch {
            attemptAutoSignIn(
                context = this@MainActivity,
                credentialManager = credentialManager,
                onAuthSuccess = { /* auth state listener updates UI */ },
                onUnauthenticated = { /* user will see SignInScreen */ },
                scope = lifecycleScope
            )
        }

        setContent {
            var currentUser by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser) }

            DisposableEffect(Unit) {
                val listener = FirebaseAuth.AuthStateListener { auth ->
                    currentUser = auth.currentUser
                }
                FirebaseAuth.getInstance().addAuthStateListener(listener)
                onDispose {
                    FirebaseAuth.getInstance().removeAuthStateListener(listener)
                }
            }

            if (currentUser == null) {
                SeniorShieldTheme {
                    val viewModel: SeniorShieldViewModel = viewModel()
                    SignInScreen(
                        speechAssistant = viewModel.speechAssistant,
                        onAuthSuccess = {
                            currentUser = FirebaseAuth.getInstance().currentUser
                            viewModel.initUserSession()
                        }
                    )
                }
            } else {
                val viewModel: SeniorShieldViewModel = viewModel()
                LaunchedEffect(currentUser?.uid) {
                    viewModel.initUserSession()
                }

                val textScale by viewModel.textScale.collectAsState()
                val currentDensity = LocalDensity.current
                val adjustedDensity = Density(
                    density = currentDensity.density,
                    fontScale = currentDensity.fontScale * textScale.multiplier
                )

                CompositionLocalProvider(LocalDensity provides adjustedDensity) {
                    SeniorShieldTheme {
                        MainAppScaffold(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScaffold(viewModel: SeniorShieldViewModel) {
    val currentTab by viewModel.currentTab.collectAsState()
    val isHospitalsOpen by viewModel.isHospitalsScreenOpen.collectAsState()
    val isScamShieldOpen by viewModel.isScamShieldOpen.collectAsState()
    val scamDestination by viewModel.scamDestination.collectAsState()

    // Back button handling
    BackHandler(enabled = isHospitalsOpen || isScamShieldOpen || currentTab != AppTab.HOME) {
        when {
            isHospitalsOpen -> viewModel.closeHospitalsScreen()
            isScamShieldOpen -> {
                if (scamDestination != NavigationDestination.HOME) {
                    viewModel.navigateScamTo(NavigationDestination.HOME)
                } else {
                    viewModel.closeScamShield()
                }
            }
            currentTab != AppTab.HOME -> viewModel.switchTab(AppTab.HOME)
        }
    }

    if (isHospitalsOpen) {
        HospitalsDirectoryScreen(viewModel = viewModel)
        return
    }

    if (isScamShieldOpen) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Senior Shield – AI Scam Detector",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.closeScamShield() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back to Main App",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = GuardianNavy)
                )
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                when (scamDestination) {
                    NavigationDestination.HOME -> HomeScreen(viewModel = viewModel)
                    NavigationDestination.CHECK_MESSAGE -> CheckMessageScreen(viewModel = viewModel)
                    NavigationDestination.CHECK_CALL -> CheckCallScreen(viewModel = viewModel)
                    NavigationDestination.LEARN -> LearnScamsScreen(viewModel = viewModel)
                    NavigationDestination.HELP -> EmergencyHelpScreen(viewModel = viewModel)
                    NavigationDestination.SETTINGS -> SettingsScreen(viewModel = viewModel)
                }
            }
        }
        return
    }

    // Main App with Bottom Navigation Bar matching reference layout (Home, Claims, Rewards, More)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp,
                windowInsets = WindowInsets.navigationBars,
                modifier = Modifier.testTag("main_bottom_nav_bar")
            ) {
                // 1. Home
                NavigationBarItem(
                    selected = currentTab == AppTab.HOME,
                    onClick = { viewModel.switchTab(AppTab.HOME) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Home",
                            fontWeight = if (currentTab == AppTab.HOME) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkPillButton,
                        selectedTextColor = DarkPillButton,
                        indicatorColor = Color(0xFFF3F4F6),
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    ),
                    modifier = Modifier.testTag("tab_home")
                )

                // 2. Claims
                NavigationBarItem(
                    selected = currentTab == AppTab.CLAIMS,
                    onClick = { viewModel.switchTab(AppTab.CLAIMS) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Folder,
                            contentDescription = "Claims",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Claims",
                            fontWeight = if (currentTab == AppTab.CLAIMS) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkPillButton,
                        selectedTextColor = DarkPillButton,
                        indicatorColor = Color(0xFFF3F4F6),
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    ),
                    modifier = Modifier.testTag("tab_claims")
                )

                // 3. Rewards
                NavigationBarItem(
                    selected = currentTab == AppTab.REWARDS,
                    onClick = { viewModel.switchTab(AppTab.REWARDS) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.CardGiftcard,
                            contentDescription = "Rewards",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Rewards",
                            fontWeight = if (currentTab == AppTab.REWARDS) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkPillButton,
                        selectedTextColor = DarkPillButton,
                        indicatorColor = Color(0xFFF3F4F6),
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    ),
                    modifier = Modifier.testTag("tab_rewards")
                )

                // 4. More
                NavigationBarItem(
                    selected = currentTab == AppTab.MORE,
                    onClick = { viewModel.switchTab(AppTab.MORE) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = "More",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "More",
                            fontWeight = if (currentTab == AppTab.MORE) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkPillButton,
                        selectedTextColor = DarkPillButton,
                        indicatorColor = Color(0xFFF3F4F6),
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    ),
                    modifier = Modifier.testTag("tab_more")
                )
            }
        }
    ) { innerPadding ->
        AnimatedContent(
            targetState = currentTab,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "tab_transition",
            modifier = Modifier.padding(innerPadding)
        ) { tab ->
            when (tab) {
                AppTab.HOME -> HealthcareHomeScreen(viewModel = viewModel)
                AppTab.CLAIMS -> ClaimsScreen(viewModel = viewModel)
                AppTab.REWARDS -> RewardsScreen(viewModel = viewModel)
                AppTab.MORE -> MoreScreen(viewModel = viewModel)
            }
        }
    }
}
