package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DashboardTab
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.PulseUiState
import com.example.viewmodel.PulseViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Pulse60Screen(
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pulse 60 🔥",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                    color = PulseTextPrimary
                )
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = PulseTealContainer
                ) {
                    Text(
                        text = "Day ${uiState.userProfile.pulse60Day} / 60",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = PulseTealPrimary,
                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Small daily actions. 60 days of consistency.",
                style = MaterialTheme.typography.bodyMedium,
                color = PulseTextSecondary
            )
        }

        // SECTION 1 — CURRENT STREAK CARD
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = PulseTealPrimary),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "DAY 17 / 60",
                            style = MaterialTheme.typography.labelSmall.copy(
                                letterSpacing = 1.2.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "17 day streak 🔥",
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp
                            ),
                            color = Color.White
                        )
                    }

                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.15f),
                        modifier = Modifier.size(54.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = Color(0xFFFBBF24),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Progress Bar: 17 of 60 days
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(17f / 60f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(4.dp))
                            .background(PulseMintAccent)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Keep showing up. Small actions add up.",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
        }

        // SECTION 2 — LAST 7 DAYS GRAPHICAL PROGRESS (KEY FEATURE)
        SevenDayHabitMatrix(
            habitDays = uiState.habitHistory,
            modifier = Modifier.testTag("seven_day_progress_chart")
        )

        // SECTION 4 (If completed) or SECTION 3 (Today's Pulse form)
        if (uiState.isTodayPulseCompleted) {
            // COMPLETION STATE CARD
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PulseSurface),
                border = androidx.compose.foundation.BorderStroke(1.2.dp, PulseGreen),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("today_pulse_completed_card")
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = CircleShape,
                        color = PulseGreenLight,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = PulseGreenDark,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Today’s Pulse complete! 🎉",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 21.sp),
                        color = PulseTextPrimary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Day 17 completed · 17 day streak 🔥",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = PulseTealPrimary
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = PulseSurfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "✓ Movement", style = MaterialTheme.typography.labelMedium.copy(color = PulseGreenDark, fontWeight = FontWeight.Bold))
                            Text(text = "✓ Meals", style = MaterialTheme.typography.labelMedium.copy(color = PulseGreenDark, fontWeight = FontWeight.Bold))
                            Text(text = "✓ Wellbeing", style = MaterialTheme.typography.labelMedium.copy(color = PulseGreenDark, fontWeight = FontWeight.Bold))
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Today has been added to your weekly progress.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PulseTextSecondary
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    PulsePrimaryButton(
                        text = "Back to Home",
                        onClick = { viewModel.navigateToTab(DashboardTab.HOME) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        } else {
            // SECTION 3 — TODAY'S PULSE (3 Questions)
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PulseSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("today_pulse_input_card")
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "TODAY’S PULSE",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = PulseTextPrimary
                            )
                            Text(
                                text = "Complete 3 quick questions (~30s)",
                                style = MaterialTheme.typography.bodySmall,
                                color = PulseTextSecondary
                            )
                        }
                        Surface(
                            shape = CircleShape,
                            color = PulseTealContainer,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "3",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = PulseTealPrimary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // QUESTION 1 — MOVEMENT
                    Text(
                        text = "1. How many minutes have you walked today?",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp, fontWeight = FontWeight.SemiBold),
                        color = PulseTextPrimary
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    val walkOptions = listOf("0–15 min", "15–30 min", "30–60 min", "60+ min")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        walkOptions.forEach { opt ->
                            val isSelected = uiState.todayPulseInput.movementMinutes == opt
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(
                                        width = if (isSelected) 1.5.dp else 1.dp,
                                        color = if (isSelected) PulseTealPrimary else PulseDivider,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .background(if (isSelected) PulseTealContainer else PulseSurfaceVariant)
                                    .clickable { viewModel.setMovementMinutes(opt) }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = opt,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 11.5.sp
                                    ),
                                    color = if (isSelected) PulseTealPrimary else PulseTextPrimary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    // Optional movement screenshot upload
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = {
                                viewModel.triggerMockUpload(
                                    title = "Upload Activity Screenshot",
                                    description = "Attach a step count or smartwatch screenshot (optional)"
                                )
                            },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = if (uiState.todayPulseInput.movementScreenshotUploaded) Icons.Default.Check else Icons.Outlined.UploadFile,
                                contentDescription = null,
                                tint = if (uiState.todayPulseInput.movementScreenshotUploaded) PulseGreenDark else PulseTealPrimary,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (uiState.todayPulseInput.movementScreenshotUploaded) "Screenshot attached ✓" else "Upload activity screenshot",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                color = if (uiState.todayPulseInput.movementScreenshotUploaded) PulseGreenDark else PulseTealPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = PulseDivider, thickness = 0.8.dp)
                    Spacer(modifier = Modifier.height(14.dp))

                    // QUESTION 2 — MEALS
                    Text(
                        text = "2. Have you had all your planned meals today?",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp, fontWeight = FontWeight.SemiBold),
                        color = PulseTextPrimary
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    val mealOptions = listOf("Yes", "Partially", "No")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        mealOptions.forEach { opt ->
                            val isSelected = uiState.todayPulseInput.mealsPlanned == opt
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(
                                        width = if (isSelected) 1.5.dp else 1.dp,
                                        color = if (isSelected) PulseTealPrimary else PulseDivider,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .background(if (isSelected) PulseTealContainer else PulseSurfaceVariant)
                                    .clickable { viewModel.setMealsPlanned(opt) }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = opt,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    ),
                                    color = if (isSelected) PulseTealPrimary else PulseTextPrimary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    // Optional meal photo upload (non-judgmental)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = {
                                viewModel.triggerMockUpload(
                                    title = "Upload Meal Photo",
                                    description = "Want to show us your meal? (Optional and non-judgmental)"
                                )
                            },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = if (uiState.todayPulseInput.mealPhotoUploaded) Icons.Default.Check else Icons.Outlined.PhotoCamera,
                                contentDescription = null,
                                tint = if (uiState.todayPulseInput.mealPhotoUploaded) PulseGreenDark else PulseTealPrimary,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (uiState.todayPulseInput.mealPhotoUploaded) "Meal photo attached ✓" else "Upload meal photo",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                color = if (uiState.todayPulseInput.mealPhotoUploaded) PulseGreenDark else PulseTealPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = PulseDivider, thickness = 0.8.dp)
                    Spacer(modifier = Modifier.height(14.dp))

                    // QUESTION 3 — WELLBEING
                    Text(
                        text = "3. How are you feeling today?",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp, fontWeight = FontWeight.SemiBold),
                        color = PulseTextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Your answers help Pulse understand your wellbeing over time.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PulseTextSecondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    val moods = listOf("😄 Great", "🙂 Good", "😐 Okay", "😟 Low", "😞 Very low")
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        moods.forEach { mood ->
                            val isSelected = uiState.todayPulseInput.wellbeingMood == mood
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(
                                        width = if (isSelected) 1.5.dp else 1.dp,
                                        color = if (isSelected) PulseTealPrimary else PulseDivider,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .background(if (isSelected) PulseTealContainer else PulseSurfaceVariant)
                                    .clickable { viewModel.setWellbeingMood(mood) }
                                    .padding(horizontal = 12.dp, vertical = 9.dp)
                            ) {
                                Text(
                                    text = mood,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    ),
                                    color = if (isSelected) PulseTealPrimary else PulseTextPrimary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Primary CTA: Complete Today's Pulse
                    PulsePrimaryButton(
                        text = "Complete Today’s Pulse",
                        onClick = { viewModel.submitTodayPulse() },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        MedicalDisclaimer()
        Spacer(modifier = Modifier.height(12.dp))
    }
}
