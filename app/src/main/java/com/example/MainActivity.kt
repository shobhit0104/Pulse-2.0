package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.DashboardTab
import com.example.model.Screen
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.*
import com.example.viewmodel.PulseUiState
import com.example.viewmodel.PulseViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: PulseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                PulseApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PulseApp(viewModel: PulseViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Handle system back navigation
    BackHandler(enabled = uiState.currentScreen != Screen.REGISTRATION) {
        when (uiState.currentScreen) {
            Screen.OTP_VERIFICATION -> viewModel.jumpToScreen(Screen.REGISTRATION)
            Screen.BUILD_PROFILE_INTRO -> viewModel.jumpToScreen(Screen.OTP_VERIFICATION)
            Screen.ASSESSMENT -> viewModel.onAssessmentBack()
            Screen.PROFILE_GENERATION -> viewModel.jumpToScreen(Screen.BUILD_PROFILE_INTRO)
            Screen.PULSE_SCORE_RESULT -> viewModel.jumpToScreen(Screen.BUILD_PROFILE_INTRO)
            Screen.MAIN_DASHBOARD -> {
                if (uiState.currentTab != DashboardTab.HOME) {
                    viewModel.navigateToTab(DashboardTab.HOME)
                }
            }
            Screen.REGISTRATION -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = uiState.currentScreen,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "screen_transition"
        ) { screen ->
            when (screen) {
                Screen.REGISTRATION -> RegistrationScreen(uiState = uiState, viewModel = viewModel)
                Screen.OTP_VERIFICATION -> OtpVerificationScreen(uiState = uiState, viewModel = viewModel)
                Screen.BUILD_PROFILE_INTRO -> BuildProfileIntroScreen(uiState = uiState, viewModel = viewModel)
                Screen.ASSESSMENT -> AssessmentScreen(uiState = uiState, viewModel = viewModel)
                Screen.PROFILE_GENERATION -> ProfileGenerationScreen(uiState = uiState)
                Screen.PULSE_SCORE_RESULT -> PulseScoreResultScreen(uiState = uiState, viewModel = viewModel)
                Screen.MAIN_DASHBOARD -> MainDashboardContainer(uiState = uiState, viewModel = viewModel)
            }
        }

        // Global Modals and Dialogs
        GlobalDialogsHost(uiState = uiState, viewModel = viewModel)
    }
}

@Composable
fun MainDashboardContainer(
    uiState: PulseUiState,
    viewModel: PulseViewModel
) {
    Scaffold(
        containerColor = PulseBackground,
        topBar = {
            Surface(
                color = PulseSurface,
                border = androidx.compose.foundation.BorderStroke(0.8.dp, PulseDivider),
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.testTag("app_brand_logo")
                    ) {
                        Box(
                            modifier = Modifier
                                .size(9.dp)
                                .clip(CircleShape)
                                .background(PulseGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "pulse",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = (-0.5).sp
                            ),
                            color = PulseTealPrimary
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = PulseTealContainer,
                            modifier = Modifier.clickable { viewModel.navigateToTab(DashboardTab.PULSE_60) }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "🔥 ${uiState.userProfile.streakDays}d",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = PulseTealPrimary
                                )
                            }
                        }

                        IconButton(
                            onClick = { viewModel.openSafetyAlertModal() },
                            modifier = Modifier.size(34.dp).testTag("safety_alert_icon")
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Shield,
                                contentDescription = "Safety",
                                tint = PulseTextSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                color = PulseSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                NavigationBar(
                    containerColor = PulseSurface,
                    contentColor = PulseTextPrimary,
                    modifier = Modifier.fillMaxWidth().testTag("bottom_nav_bar")
                ) {
                    DashboardTab.values().forEach { tab ->
                        val isSelected = uiState.currentTab == tab
                        val (icon, selectedIcon) = when (tab) {
                            DashboardTab.HOME -> Pair(Icons.Outlined.Home, Icons.Filled.Home)
                            DashboardTab.PULSE_60 -> Pair(Icons.Outlined.LocalFireDepartment, Icons.Filled.LocalFireDepartment)
                            DashboardTab.TRACK -> Pair(Icons.Outlined.Timeline, Icons.Filled.Timeline)
                            DashboardTab.PROFILE -> Pair(Icons.Outlined.Person, Icons.Filled.Person)
                            DashboardTab.SETTINGS -> Pair(Icons.Outlined.Settings, Icons.Filled.Settings)
                        }

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.navigateToTab(tab) },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) selectedIcon else icon,
                                    contentDescription = tab.label,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = tab.label,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 11.sp
                                    )
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PulseTealPrimary,
                                selectedTextColor = PulseTealPrimary,
                                indicatorColor = PulseTealContainer,
                                unselectedIconColor = PulseTextMuted,
                                unselectedTextColor = PulseTextMuted
                            ),
                            modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                        )
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (uiState.currentTab) {
                DashboardTab.HOME -> HomeScreen(uiState = uiState, viewModel = viewModel)
                DashboardTab.PULSE_60 -> Pulse60Screen(uiState = uiState, viewModel = viewModel)
                DashboardTab.TRACK -> TrackScreen(uiState = uiState, viewModel = viewModel)
                DashboardTab.PROFILE -> ProfileScreen(uiState = uiState, viewModel = viewModel)
                DashboardTab.SETTINGS -> SettingsScreen(uiState = uiState, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun GlobalDialogsHost(
    uiState: PulseUiState,
    viewModel: PulseViewModel
) {
    // 1. Doctor Profile Modal
    uiState.selectedDoctorForModal?.let { doc ->
        DoctorProfileModal(
            doctor = doc,
            onClose = { viewModel.closeDoctorModal() },
            onBookConsultation = { doctorName -> viewModel.bookConsultation(doctorName) }
        )
    }

    // 2. Consultation Confirmation Dialog
    if (uiState.showConsultationSuccessDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissConsultationSuccess() },
            title = {
                Text(
                    text = "Consultation request created.",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = PulseTextPrimary
                )
            },
            text = {
                Column {
                    Text(
                        text = "We have notified ${uiState.bookedDoctorName ?: "the clinic"}. Their care coordinator will reach out to confirm your scheduled slot.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PulseTextSecondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Recommendations are based on the information you provided and are not a medical diagnosis.",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                        color = PulseTextMuted
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.dismissConsultationSuccess() },
                    colors = ButtonDefaults.buttonColors(containerColor = PulseTealPrimary)
                ) {
                    Text("Done", color = Color.White)
                }
            },
            shape = RoundedCornerShape(18.dp),
            containerColor = PulseSurface
        )
    }

    // 3. 30-Day Doctor Summary Modal
    if (uiState.showDoctorSummaryModal) {
        DoctorSummaryModal(
            onDismiss = { viewModel.closeDoctorSummaryModal() },
            onShareWithDoctor = { viewModel.shareDoctorSummary() }
        )
    }

    // 4. Share Summary Confirmation Dialog
    if (uiState.showShareSummarySuccess) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissShareSummarySuccess() },
            title = {
                Text(
                    text = "Summary ready to share.",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = PulseTextPrimary
                )
            },
            text = {
                Text(
                    text = "Your 30-day health overview has been compiled into a doctor-friendly PDF format. You can present this directly during your clinical consultation.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PulseTextSecondary
                )
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.dismissShareSummarySuccess() },
                    colors = ButtonDefaults.buttonColors(containerColor = PulseTealPrimary)
                ) {
                    Text("Great", color = Color.White)
                }
            },
            shape = RoundedCornerShape(18.dp),
            containerColor = PulseSurface
        )
    }

    // 5. Health Report Upload Modal
    if (uiState.showReportUploadModal) {
        ReportUploadModal(
            onDismiss = { viewModel.closeReportUploadModal() },
            onUploadSelected = { reportType -> viewModel.submitReportUpload(reportType) }
        )
    }

    // 6. Report Uploaded Success Dialog
    if (uiState.showReportUploadSuccess) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissReportUploadSuccess() },
            title = {
                Text(
                    text = "Report added to your Health Timeline ✓",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = PulseTealPrimary
                )
            },
            text = {
                Text(
                    text = "Your ${uiState.uploadedReportType ?: "health"} report is now securely filed in your timeline. You can review it anytime or include it in future doctor summaries.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PulseTextSecondary
                )
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.dismissReportUploadSuccess() },
                    colors = ButtonDefaults.buttonColors(containerColor = PulseTealPrimary)
                ) {
                    Text("OK", color = Color.White)
                }
            },
            shape = RoundedCornerShape(18.dp),
            containerColor = PulseSurface
        )
    }

    // 7. Privacy Modal
    if (uiState.showPrivacyModal) {
        PrivacyCenterModal(onDismiss = { viewModel.closePrivacyModal() })
    }

    // 8. Safety Alert Modal (Page 13)
    if (uiState.showSafetyAlertModal) {
        SafetyAttentionDialog(
            onDismiss = { viewModel.closeSafetyAlertModal() },
            onContactDoctor = {
                viewModel.navigateToTab(DashboardTab.HOME)
            }
        )
    }

    // 9. Mock Photo/Screenshot Upload Dialog
    if (uiState.showMockUploadDialog) {
        MockUploadDialog(
            title = uiState.mockUploadTitle,
            description = uiState.mockUploadDescription,
            onDismiss = { viewModel.closeMockUploadDialog() },
            onConfirm = { viewModel.confirmMockUpload() }
        )
    }
}
