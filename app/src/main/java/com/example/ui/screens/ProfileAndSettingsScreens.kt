package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DashboardTab
import com.example.model.Screen
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.PulseUiState
import com.example.viewmodel.PulseViewModel

@Composable
fun ProfileScreen(
    uiState: PulseUiState,
    viewModel: PulseViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Header
        Column {
            Text(
                text = "Your Pulse Profile",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                color = PulseTextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Personal health awareness snapshot and preferences",
                style = MaterialTheme.typography.bodyMedium,
                color = PulseTextSecondary
            )
        }

        // User Identity Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = PulseTealContainer,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = uiState.userProfile.fullName.take(1),
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                color = PulseTealPrimary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = uiState.userProfile.fullName,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = PulseTextPrimary
                        )
                        Text(
                            text = "${uiState.userProfile.age} yrs · ${uiState.userProfile.city}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = PulseTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
                HorizontalDivider(color = PulseDivider, thickness = 0.8.dp)
                Spacer(modifier = Modifier.height(14.dp))

                // Detail rows
                ProfileInfoRow(label = "Existing condition", value = uiState.userProfile.chronicCondition)
                Spacer(modifier = Modifier.height(8.dp))
                ProfileInfoRow(label = "Health priorities", value = uiState.userProfile.priorities.joinToString(", "))
                Spacer(modifier = Modifier.height(8.dp))
                ProfileInfoRow(label = "Recent blood pressure", value = uiState.userProfile.recentBloodPressure)
            }
        }

        // YOUR PULSE SCORE CARD
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = PulseTealContainer.copy(alpha = 0.5f)),
            border = androidx.compose.foundation.BorderStroke(1.2.dp, PulseTealPrimary.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "YOUR PULSE",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                        color = PulseTealPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${uiState.userProfile.pulseScore} / 100",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 28.sp),
                        color = PulseTealPrimary
                    )
                    Text(
                        text = "Good foundation · +6 pts",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = PulseGreenDark
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = PulseSurface,
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = PulseTealPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        // PRIVACY & CONSENT SECTION
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Shield,
                        contentDescription = null,
                        tint = PulseTealPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Privacy & Consent",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = PulseTextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Your health data stays under your control.",
                    style = MaterialTheme.typography.bodySmall,
                    color = PulseTextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                PrivacyStatusRow(title = "Health information", status = "Private")
                Spacer(modifier = Modifier.height(10.dp))
                PrivacyStatusRow(title = "Doctor sharing", status = "Only when you choose")
                Spacer(modifier = Modifier.height(10.dp))
                PrivacyStatusRow(title = "Family sharing", status = "Not enabled")

                Spacer(modifier = Modifier.height(18.dp))

                PulseSecondaryButton(
                    text = "Manage Privacy",
                    onClick = { viewModel.openPrivacyModal() },
                    modifier = Modifier.fillMaxWidth().testTag("manage_privacy_btn")
                )
            }
        }

        MedicalDisclaimer()
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = PulseTextSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = PulseTextPrimary
        )
    }
}

@Composable
private fun PrivacyStatusRow(title: String, status: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = PulseTextPrimary
        )
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = PulseSurfaceVariant
        ) {
            Text(
                text = status,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium, fontSize = 11.5.sp),
                color = PulseTextSecondary,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
        }
    }
}

/**
 * Settings Screen with Hackathon Demo Tools for Judges
 */
@Composable
fun SettingsScreen(
    uiState: PulseUiState,
    viewModel: PulseViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Column {
            Text(
                text = "Settings & Prototype Demo",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                color = PulseTextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Hackathon jury controls, navigation shortcuts & safety state",
                style = MaterialTheme.typography.bodyMedium,
                color = PulseTextSecondary
            )
        }

        // JURY DEMO SHORTCUTS
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Fast Journey Navigation",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = PulseTextPrimary
                )
                Text(
                    text = "Jump directly to any step in the hackathon presentation",
                    style = MaterialTheme.typography.bodySmall,
                    color = PulseTextSecondary
                )
                Spacer(modifier = Modifier.height(14.dp))

                val journeySteps = listOf(
                    Pair("1. Registration", { viewModel.jumpToScreen(Screen.REGISTRATION) }),
                    Pair("2. OTP Verification", { viewModel.jumpToScreen(Screen.OTP_VERIFICATION) }),
                    Pair("3. Assessment Intro", { viewModel.jumpToScreen(Screen.BUILD_PROFILE_INTRO) }),
                    Pair("4. 10-Question Assessment", { viewModel.jumpToScreen(Screen.ASSESSMENT) }),
                    Pair("5. Pulse Score (78/100)", { viewModel.jumpToScreen(Screen.PULSE_SCORE_RESULT) }),
                    Pair("6. Home Dashboard", { viewModel.jumpToScreen(Screen.MAIN_DASHBOARD, DashboardTab.HOME) }),
                    Pair("7. Pulse 60 Habit Loop", { viewModel.jumpToScreen(Screen.MAIN_DASHBOARD, DashboardTab.PULSE_60) }),
                    Pair("8. Track & 30-Day Trends", { viewModel.jumpToScreen(Screen.MAIN_DASHBOARD, DashboardTab.TRACK) }),
                    Pair("9. Profile & Privacy", { viewModel.jumpToScreen(Screen.MAIN_DASHBOARD, DashboardTab.PROFILE) })
                )

                journeySteps.forEach { (label, action) ->
                    OutlinedButton(
                        onClick = action,
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = label, style = MaterialTheme.typography.labelMedium, color = PulseTextPrimary)
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = PulseTealPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        // PAGE 13 — SAFETY / ATTENTION STATE DEMO
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PulseAlertContainer),
            border = androidx.compose.foundation.BorderStroke(1.dp, PulseAlert.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Safety Attention State (Page 13)",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = PulseAlert
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Pulse non-diagnostic safety guardrail alert component",
                    style = MaterialTheme.typography.bodySmall,
                    color = PulseTextPrimary
                )
                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = { viewModel.openSafetyAlertModal() },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PulseAlert),
                    modifier = Modifier.fillMaxWidth().testTag("trigger_safety_alert_btn")
                ) {
                    Text(text = "Preview Safety Alert Component", color = Color.White)
                }
            }
        }

        // RESET DEMO BUTTON
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Reset Prototype State",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = PulseTextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Clears all inputs and restarts demo from registration.",
                    style = MaterialTheme.typography.bodySmall,
                    color = PulseTextSecondary
                )
                Spacer(modifier = Modifier.height(14.dp))
                PulseSecondaryButton(
                    text = "Reset Demo to Beginning",
                    onClick = { viewModel.resetDemo() },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        MedicalDisclaimer()
        Spacer(modifier = Modifier.height(12.dp))
    }
}
