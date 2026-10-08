package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import com.example.model.TimelineItem
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.PulseUiState
import com.example.viewmodel.PulseViewModel

@Composable
fun TrackScreen(
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
        // Heading
        Column {
            Text(
                text = "Your Progress",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                color = PulseTextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "See how your health habits are changing over time.",
                style = MaterialTheme.typography.bodyMedium,
                color = PulseTextSecondary
            )
        }

        // SECTION 1 — PULSE SCORE TREND (LINE CHART)
        PulseTrendLineChart(modifier = Modifier.testTag("track_line_chart"))

        // SECTION 2 — HEALTH TRENDS
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Health Trends",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = PulseTextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "30-day directional shifts across core metrics",
                    style = MaterialTheme.typography.bodySmall,
                    color = PulseTextSecondary
                )
                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TrendCard(
                        title = "Sleep",
                        value = "${uiState.userProfile.avgSleepHours} hrs",
                        change = "↑ 14%",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                    TrendCard(
                        title = "Activity",
                        value = "${uiState.userProfile.avgActivityMins} min/day",
                        change = "↑ 18%",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TrendCard(
                        title = "Stress",
                        value = "${uiState.userProfile.avgStressScore} / 10",
                        change = "↓ 12%",
                        isPositive = true, // Lower stress is positive!
                        modifier = Modifier.weight(1f)
                    )
                    TrendCard(
                        title = "Med adherence",
                        value = "${uiState.userProfile.medicationAdherence}%",
                        change = "↑ 7%",
                        isPositive = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // SECTION 4 — DOCTOR SUMMARY CARD (PREPARE FOR VISIT)
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PulseTealContainer.copy(alpha = 0.6f)),
            border = androidx.compose.foundation.BorderStroke(1.2.dp, PulseTealPrimary.copy(alpha = 0.3f)),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("doctor_summary_preview_card")
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = PulseTealPrimary,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.MedicalInformation,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Prepare for your next doctor visit",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = PulseTealPrimary
                        )
                        Text(
                            text = "Pulse can turn your recent health activity into a simple summary.",
                            style = MaterialTheme.typography.bodySmall,
                            color = PulseTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = PulseSurface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "LAST 30 DAYS SNAPSHOT",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                            color = PulseTextMuted
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(text = "Sleep: 6.4 hrs avg", style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.5.sp), color = PulseTextPrimary)
                                Text(text = "Activity: 4,850 min/mo", style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.5.sp), color = PulseTextPrimary)
                            }
                            Column {
                                Text(text = "Med adherence: 89%", style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.5.sp), color = PulseTextPrimary)
                                Text(text = "Stress: 5.2 / 10", style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.5.sp), color = PulseTextPrimary)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider(color = PulseDivider, thickness = 0.8.dp)
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Changes noticed: ↑ Sleep improved  ↑ Activity improved  → BP stable",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp, fontWeight = FontWeight.Medium),
                            color = PulseTealPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                PulsePrimaryButton(
                    text = "View Doctor Summary",
                    onClick = { viewModel.openDoctorSummaryModal() },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // SECTION 5 — HEALTH REPORT UPLOAD CARD
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
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
                        text = "HAVE A HEALTH REPORT?",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 0.8.sp),
                        color = PulseTealPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Upload it to keep your health information organized.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PulseTextSecondary
                    )
                }
                Button(
                    onClick = { viewModel.openReportUploadModal() },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PulseTealPrimary),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                    modifier = Modifier.testTag("upload_report_btn")
                ) {
                    Text(
                        text = "Upload Report",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold, color = Color.White)
                    )
                }
            }
        }

        // SECTION 3 — HEALTH TIMELINE
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Your Health Timeline",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = PulseTextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Chronological record of vital check-ins and reports",
                    style = MaterialTheme.typography.bodySmall,
                    color = PulseTextSecondary
                )
                Spacer(modifier = Modifier.height(16.dp))

                uiState.timelineList.forEachIndexed { index, item ->
                    TimelineRow(item = item, isLast = index == uiState.timelineList.size - 1)
                }
            }
        }

        MedicalDisclaimer()
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun TrendCard(
    title: String,
    value: String,
    change: String,
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
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                color = PulseTextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                    color = PulseTextPrimary
                )
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (isPositive) PulseGreenLight else PulseAlertContainer
                ) {
                    Text(
                        text = change,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        ),
                        color = if (isPositive) PulseGreenDark else PulseAlert,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TimelineRow(
    item: TimelineItem,
    isLast: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Date column
        Text(
            text = item.date,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold, fontSize = 12.sp),
            color = PulseTextPrimary,
            modifier = Modifier.width(72.dp)
        )

        // Line and node
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(PulseTealPrimary)
            )
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(36.dp)
                        .background(PulseDivider)
                )
            }
        }

        Spacer(modifier = Modifier.width(6.dp))

        // Content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (isLast) 0.dp else 16.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                color = PulseTextPrimary
            )
            if (item.subtitle != null) {
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                    color = PulseTextSecondary
                )
            }
        }
    }
}
