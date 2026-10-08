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
import com.example.model.*
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.DoctorPriyaSharma
import com.example.viewmodel.DoctorRahulMehta
import com.example.viewmodel.PulseChangeReasons
import com.example.viewmodel.PulseUiState
import com.example.viewmodel.PulseViewModel

@Composable
fun HomeScreen(
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Good morning, ${uiState.userProfile.fullName} 👋",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    ),
                    color = PulseTextPrimary
                )
                Text(
                    text = "Here’s your health snapshot for today.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PulseTextSecondary
                )
            }

            Surface(
                shape = CircleShape,
                color = PulseTealContainer,
                modifier = Modifier
                    .size(42.dp)
                    .clickable { viewModel.navigateToTab(DashboardTab.PROFILE) }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = uiState.userProfile.fullName.take(1),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = PulseTealPrimary
                    )
                }
            }
        }

        // SECTION 1 — YOUR PULSE CARD
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = PulseTealPrimary),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("home_pulse_card")
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "YOUR PULSE",
                        style = MaterialTheme.typography.labelSmall.copy(
                            letterSpacing = 1.2.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "${uiState.userProfile.pulseScore}",
                            style = MaterialTheme.typography.displayLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 44.sp
                            ),
                            color = Color.White
                        )
                        Text(
                            text = " / 100",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "Good foundation",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "↑ 6 points this month",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = PulseMintAccent
                        )
                    }
                }

                // Heartbeat wave visual accent
                Surface(
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.15f),
                    modifier = Modifier.size(54.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }

        // SECTION 2 — WHY DID MY PULSE CHANGE?
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("why_pulse_changed_card")
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Why did my Pulse change?",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = PulseTextPrimary
                    )
                    TextButton(
                        onClick = { viewModel.navigateToTab(DashboardTab.TRACK) },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "View my progress",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = PulseTealPrimary
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = PulseTealPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                PulseChangeReasons.forEach { reason ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (reason.isPositive) PulseGreenLight else PulseAlertContainer,
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Text(
                                text = reason.changeText,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.5.sp),
                                color = if (reason.isPositive) PulseGreenDark else PulseAlert,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = reason.metric,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = PulseTextPrimary
                            )
                            Text(
                                text = reason.detail,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                color = PulseTextSecondary
                            )
                        }
                    }
                }
            }
        }

        // SECTION 3 — PULSE 60 PREVIEW CARD
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("pulse_60_preview_card")
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "🔥 PULSE 60",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PulseTealPrimary
                            )
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = PulseTealContainer
                    ) {
                        Text(
                            text = "${uiState.userProfile.streakDays} day streak 🔥",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = PulseTealPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Build healthier habits in 60 days · Day ${uiState.userProfile.pulse60Day} / 60",
                    style = MaterialTheme.typography.bodySmall,
                    color = PulseTextSecondary
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Mini 7-day indicators preview (M T W T F S S)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    uiState.habitHistory.forEach { dayRecord ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = dayRecord.dayShort,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                color = PulseTextMuted
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(
                                shape = CircleShape,
                                color = when {
                                    dayRecord.isFullyCompleted -> PulseGreenLight
                                    dayRecord.isPartiallyCompleted -> PulseWarningContainer
                                    else -> PulseSurfaceVariant
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = when {
                                            dayRecord.isFullyCompleted -> "✓"
                                            dayRecord.isPartiallyCompleted -> "◐"
                                            else -> "○"
                                        },
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp
                                        ),
                                        color = when {
                                            dayRecord.isFullyCompleted -> PulseGreenDark
                                            dayRecord.isPartiallyCompleted -> PulseWarning
                                            else -> PulseTextMuted
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "6 of 7 days completed",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = PulseTextSecondary
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = "✓ Movement", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp), color = PulseTealPrimary)
                        Text(text = "✓ Meals", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp), color = PulseGreenDark)
                        Text(text = "○ Wellbeing", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp), color = PulseTextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                PulsePrimaryButton(
                    text = "Complete today’s Pulse →",
                    onClick = { viewModel.navigateToTab(DashboardTab.PULSE_60) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // SECTION 4 — FOCUS AREAS
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Focus Areas",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = PulseTextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Key parameters identified for continuous awareness",
                    style = MaterialTheme.typography.bodySmall,
                    color = PulseTextSecondary
                )
                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FocusTile(
                        title = "Sleep",
                        value = "${uiState.userProfile.avgSleepHours} hrs avg",
                        status = "Improving",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                    FocusTile(
                        title = "Stress & wellbeing",
                        value = "${uiState.userProfile.avgStressScore} / 10",
                        status = "Needs attention",
                        isPositive = false,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FocusTile(
                        title = "Activity",
                        value = "${uiState.userProfile.avgActivityMins} min/day",
                        status = "Good consistency",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                    FocusTile(
                        title = "Health monitoring",
                        value = "Active",
                        status = "Steady rhythm",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // SECTION 5 — YOUR PRIORITIES
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Your Priorities",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = PulseTextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Selected during your Pulse Profile assessment",
                    style = MaterialTheme.typography.bodySmall,
                    color = PulseTextSecondary
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    uiState.userProfile.priorities.forEach { priority ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = PulseTealContainer.copy(alpha = 0.5f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, PulseTealPrimary.copy(alpha = 0.2f)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (priority.contains("Sleep")) Icons.Outlined.Bedtime else Icons.Outlined.SelfImprovement,
                                    contentDescription = null,
                                    tint = PulseTealPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = priority,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = PulseTealPrimary
                                )
                            }
                        }
                    }
                }
            }
        }

        // SECTION 6 — PROFESSIONAL GUIDANCE
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Professional guidance",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = PulseTextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Based on your health profile, you may benefit from speaking with a healthcare professional.",
                style = MaterialTheme.typography.bodySmall,
                color = PulseTextSecondary
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Doctor 1
            DoctorCard(
                doctor = DoctorPriyaSharma,
                onViewDoctor = { viewModel.openDoctorModal(DoctorPriyaSharma) },
                onBookConsultation = { viewModel.bookConsultation(DoctorPriyaSharma.name) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Doctor 2
            DoctorCard(
                doctor = DoctorRahulMehta,
                onViewDoctor = { viewModel.openDoctorModal(DoctorRahulMehta) },
                onBookConsultation = { viewModel.bookConsultation(DoctorRahulMehta.name) }
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Recommendations are based on the information you provided and are not a medical diagnosis.",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                color = PulseTextMuted
            )
        }

        // SECTION 7 — DAILY CHECK-IN CARD
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurfaceVariant),
            border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Today’s check-in",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = PulseTextPrimary
                    )
                    Text(
                        text = "30 seconds to keep your Pulse updated.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PulseTextSecondary
                    )
                }
                Button(
                    onClick = { viewModel.navigateToTab(DashboardTab.PULSE_60) },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PulseTealPrimary),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Check in now →",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold, color = Color.White)
                    )
                }
            }
        }

        MedicalDisclaimer()
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun FocusTile(
    title: String,
    value: String,
    status: String,
    isPositive: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = PulseSurfaceVariant,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                color = PulseTextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                color = PulseTextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = if (isPositive) PulseGreenLight else PulseWarningContainer
            ) {
                Text(
                    text = status,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp, fontWeight = FontWeight.Medium),
                    color = if (isPositive) PulseGreenDark else Color(0xFF92400E),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}
