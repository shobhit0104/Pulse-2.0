package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import com.example.model.AssessmentState
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.PulseUiState
import com.example.viewmodel.PulseViewModel

@Composable
fun BuildProfileIntroScreen(
    uiState: PulseUiState,
    viewModel: PulseViewModel,
    modifier: Modifier = Modifier
) {
    Scaffold(
        containerColor = PulseBackground,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Pulse Logo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
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

            Spacer(modifier = Modifier.height(28.dp))

            // Hero Card
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = PulseSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = CircleShape,
                        color = PulseTealContainer,
                        modifier = Modifier.size(68.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Outlined.Assignment,
                                contentDescription = null,
                                tint = PulseTealPrimary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Build your Pulse Profile",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        textAlign = TextAlign.Center,
                        color = PulseTextPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Answer a few simple questions to help us understand your health and wellbeing.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = PulseTextSecondary
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Progress indicators
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = PulseSurfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "10 questions",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = PulseTextPrimary
                                )
                                Text(
                                    text = "~2 minutes to complete",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = PulseTextSecondary
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = PulseTealContainer
                            ) {
                                Text(
                                    text = "0 of 10 completed",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = PulseTealPrimary,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Start Assessment Button
                    PulsePrimaryButton(
                        text = "Start Assessment →",
                        onClick = { viewModel.onStartAssessmentClicked() },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Privacy message
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = PulseTextMuted,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Your answers are used to personalize your Pulse experience.",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = PulseTextSecondary,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            MedicalDisclaimer()
        }
    }
}

/**
 * 10-Question Focused Questionnaire (ONE QUESTION PER SCREEN)
 */
@Composable
fun AssessmentScreen(
    uiState: PulseUiState,
    viewModel: PulseViewModel,
    modifier: Modifier = Modifier
) {
    val qIndex = uiState.assessmentState.currentQuestionIndex
    val assessment = uiState.assessmentState

    Scaffold(
        containerColor = PulseBackground,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PulseBackground)
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.onAssessmentBack() },
                        modifier = Modifier.size(36.dp).testTag("assessment_back_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = PulseTextPrimary
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Build your Pulse Profile",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = PulseTextPrimary,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                PulseProgressBar(currentStep = qIndex + 1, totalSteps = 10)
            }
        },
        bottomBar = {
            Surface(
                color = PulseSurface,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (qIndex > 0) {
                        OutlinedButton(
                            onClick = { viewModel.onAssessmentBack() },
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                        ) {
                            Text("Back", color = PulseTextPrimary)
                        }
                    }

                    Button(
                        onClick = { viewModel.onAssessmentNext() },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PulseTealPrimary),
                        modifier = Modifier
                            .weight(if (qIndex > 0) 2f else 1f)
                            .height(50.dp)
                            .testTag("assessment_next_btn")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (qIndex == 9) "Create My Pulse Profile →" else "Next →",
                                style = MaterialTheme.typography.labelLarge.copy(fontSize = 15.sp),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            when (qIndex) {
                0 -> Question1Screen(assessment, viewModel)
                1 -> Question2Screen(assessment, viewModel)
                2 -> Question3Screen(assessment, viewModel)
                3 -> Question4Screen(assessment, viewModel)
                4 -> Question5Screen(assessment, viewModel)
                5 -> Question6Screen(assessment, viewModel)
                6 -> Question7Screen(assessment, viewModel)
                7 -> Question8Screen(assessment, viewModel)
                8 -> Question9Screen(assessment, viewModel)
                9 -> Question10Screen(assessment, viewModel)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun Question1Screen(assessment: AssessmentState, viewModel: PulseViewModel) {
    Text(
        text = "How would you describe your health right now?",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 21.sp),
        color = PulseTextPrimary
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Choose the option that best reflects your current day-to-day feeling.",
        style = MaterialTheme.typography.bodyMedium,
        color = PulseTextSecondary
    )
    Spacer(modifier = Modifier.height(20.dp))

    val options = listOf("Very good", "Good", "Okay", "Not very good", "Poor")
    options.forEach { option ->
        SelectionCard(
            title = option,
            isSelected = assessment.q1HealthGeneral == option,
            onClick = { viewModel.setQ1HealthGeneral(option) }
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Question2Screen(assessment: AssessmentState, viewModel: PulseViewModel) {
    Text(
        text = "Do you currently have any chronic or long-term health condition?",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 21.sp),
        color = PulseTextPrimary
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "This allows Pulse to personalize your awareness areas responsibly.",
        style = MaterialTheme.typography.bodyMedium,
        color = PulseTextSecondary
    )
    Spacer(modifier = Modifier.height(20.dp))

    val mainOptions = listOf(
        "No",
        "Yes — one condition",
        "Yes — more than one",
        "I'm not sure",
        "Prefer not to say"
    )
    mainOptions.forEach { option ->
        SelectionCard(
            title = option,
            isSelected = assessment.q2HasChronic == option,
            onClick = { viewModel.setQ2HasChronic(option) }
        )
        Spacer(modifier = Modifier.height(10.dp))
    }

    if (assessment.q2HasChronic.startsWith("Yes")) {
        Spacer(modifier = Modifier.height(14.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurfaceVariant),
            border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Which condition are you currently managing?",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = PulseTextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Select all that apply:",
                    style = MaterialTheme.typography.bodySmall,
                    color = PulseTextSecondary
                )
                Spacer(modifier = Modifier.height(12.dp))

                val conditions = listOf(
                    "PCOS",
                    "Diabetes / high blood sugar",
                    "High blood pressure",
                    "Heart condition",
                    "Thyroid condition",
                    "Asthma / respiratory condition",
                    "Kidney condition",
                    "Liver condition",
                    "Arthritis / joint condition",
                    "Mental wellbeing condition",
                    "Other",
                    "Prefer not to say"
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    conditions.forEach { condition ->
                        MultiSelectChip(
                            text = condition,
                            isSelected = assessment.q2SelectedConditions.contains(condition),
                            onClick = { viewModel.toggleQ2Condition(condition) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Question3Screen(assessment: AssessmentState, viewModel: PulseViewModel) {
    Text(
        text = "Are you currently taking regular medication or following a treatment plan?",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 21.sp),
        color = PulseTextPrimary
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Understanding your routine helps Pulse provide supportive reminders.",
        style = MaterialTheme.typography.bodyMedium,
        color = PulseTextSecondary
    )
    Spacer(modifier = Modifier.height(20.dp))

    val options = listOf(
        "No",
        "Yes, and I follow it regularly",
        "Yes, but I sometimes miss it",
        "Yes, but I frequently miss it",
        "Not applicable / Prefer not to say"
    )
    options.forEach { option ->
        SelectionCard(
            title = option,
            isSelected = assessment.q3Medication == option,
            onClick = { viewModel.setQ3Medication(option) }
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Question4Screen(assessment: AssessmentState, viewModel: PulseViewModel) {
    Text(
        text = "Have you noticed any change in your usual health recently?",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 21.sp),
        color = PulseTextPrimary
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Reflecting on recent shifts helps identify emerging patterns early.",
        style = MaterialTheme.typography.bodyMedium,
        color = PulseTextSecondary
    )
    Spacer(modifier = Modifier.height(20.dp))

    val options = listOf(
        "No noticeable change",
        "A small change",
        "A noticeable change",
        "A significant change",
        "I'm not sure"
    )
    options.forEach { option ->
        SelectionCard(
            title = option,
            isSelected = assessment.q4NoticedChange == option,
            onClick = { viewModel.setQ4NoticedChange(option) }
        )
        Spacer(modifier = Modifier.height(10.dp))
    }

    if (assessment.q4NoticedChange != "No noticeable change") {
        Spacer(modifier = Modifier.height(14.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurfaceVariant),
            border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "What have you noticed?",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = PulseTextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Select any areas that felt different:",
                    style = MaterialTheme.typography.bodySmall,
                    color = PulseTextSecondary
                )
                Spacer(modifier = Modifier.height(12.dp))

                val chips = listOf(
                    "Energy", "Sleep", "Weight", "Appetite",
                    "Pain / discomfort", "Mood", "Breathing",
                    "Blood sugar", "Blood pressure", "Other"
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    chips.forEach { chip ->
                        MultiSelectChip(
                            text = chip,
                            isSelected = assessment.q4ChangeChips.contains(chip),
                            onClick = { viewModel.toggleQ4ChangeChip(chip) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Question5Screen(assessment: AssessmentState, viewModel: PulseViewModel) {
    Text(
        text = "How well do you usually sleep?",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 21.sp),
        color = PulseTextPrimary
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Sleep quality is a primary pillar of physiological recovery.",
        style = MaterialTheme.typography.bodyMedium,
        color = PulseTextSecondary
    )
    Spacer(modifier = Modifier.height(20.dp))

    val qualityOptions = listOf(
        "Very well",
        "Well",
        "Sometimes disturbed",
        "Often disturbed",
        "Very poorly"
    )
    qualityOptions.forEach { option ->
        SelectionCard(
            title = option,
            isSelected = assessment.q5SleepQuality == option,
            onClick = { viewModel.setQ5SleepQuality(option) }
        )
        Spacer(modifier = Modifier.height(10.dp))
    }

    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Typical sleep duration",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
        color = PulseTextPrimary
    )
    Spacer(modifier = Modifier.height(10.dp))

    val durationOptions = listOf(
        "Less than 5 hours",
        "5–6 hours",
        "6–7 hours",
        "7–8 hours",
        "8+ hours"
    )
    durationOptions.forEach { opt ->
        SelectionCard(
            title = opt,
            isSelected = assessment.q5SleepDuration == opt,
            onClick = { viewModel.setQ5SleepDuration(opt) }
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun Question6Screen(assessment: AssessmentState, viewModel: PulseViewModel) {
    Text(
        text = "How physically active are you during a typical week?",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 21.sp),
        color = PulseTextPrimary
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Includes walking, workouts, sports, or active commuting.",
        style = MaterialTheme.typography.bodyMedium,
        color = PulseTextSecondary
    )
    Spacer(modifier = Modifier.height(20.dp))

    val activityOptions = listOf(
        Pair("Mostly inactive", Icons.Outlined.Weekend),
        Pair("Lightly active", Icons.Outlined.DirectionsWalk),
        Pair("Moderately active", Icons.Outlined.FitnessCenter),
        Pair("Very active", Icons.Outlined.DirectionsRun)
    )

    activityOptions.forEach { (option, icon) ->
        SelectionCard(
            title = option,
            isSelected = assessment.q6PhysicalActivity == option,
            onClick = { viewModel.setQ6PhysicalActivity(option) },
            icon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (assessment.q6PhysicalActivity == option) PulseTealPrimary else PulseTextMuted,
                    modifier = Modifier.size(24.dp)
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun Question7Screen(assessment: AssessmentState, viewModel: PulseViewModel) {
    Text(
        text = "How would you describe your everyday eating habits?",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 21.sp),
        color = PulseTextPrimary
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Consider meal regularity, home cooking, and balanced nutrition.",
        style = MaterialTheme.typography.bodyMedium,
        color = PulseTextSecondary
    )
    Spacer(modifier = Modifier.height(20.dp))

    val options = listOf(
        "Very healthy",
        "Mostly healthy",
        "Mixed",
        "Often unhealthy",
        "Very unhealthy"
    )
    options.forEach { option ->
        SelectionCard(
            title = option,
            isSelected = assessment.q7DietHabits == option,
            onClick = { viewModel.setQ7DietHabits(option) }
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun Question8Screen(assessment: AssessmentState, viewModel: PulseViewModel) {
    Text(
        text = "How often do you feel stressed, anxious, or emotionally overwhelmed?",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 21.sp),
        color = PulseTextPrimary
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Work deadlines, caregiving, or general pressure.",
        style = MaterialTheme.typography.bodyMedium,
        color = PulseTextSecondary
    )
    Spacer(modifier = Modifier.height(20.dp))

    val options = listOf("Never", "Rarely", "Sometimes", "Often", "Almost always")
    options.forEach { option ->
        SelectionCard(
            title = option,
            isSelected = assessment.q8StressFrequency == option,
            onClick = { viewModel.setQ8StressFrequency(option) }
        )
        Spacer(modifier = Modifier.height(10.dp))
    }

    Spacer(modifier = Modifier.height(14.dp))
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = PulseSurfaceVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = PulseTextMuted,
                modifier = Modifier.size(16.dp).padding(top = 2.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "This helps us understand your overall wellbeing. It is not a medical diagnosis.",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                color = PulseTextSecondary
            )
        }
    }
}

@Composable
private fun Question9Screen(assessment: AssessmentState, viewModel: PulseViewModel) {
    // Visually emphasized as an important Pulse question
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = PulseTealContainer,
        modifier = Modifier.padding(bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = PulseTealPrimary,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "CORE PULSE METRIC",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                ),
                color = PulseTealPrimary
            )
        }
    }

    Text(
        text = "How regularly do you monitor your health?",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 21.sp),
        color = PulseTextPrimary
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Such as checking vitals, tracking symptoms, reviewing lab reports, or using wearables.",
        style = MaterialTheme.typography.bodyMedium,
        color = PulseTextSecondary
    )
    Spacer(modifier = Modifier.height(20.dp))

    val options = listOf(
        "I regularly monitor my health",
        "I monitor when I remember",
        "I monitor only when something feels wrong",
        "I usually don't monitor it",
        "I don't know what I should monitor"
    )
    options.forEach { option ->
        SelectionCard(
            title = option,
            isSelected = assessment.q9MonitoringHabit == option,
            onClick = { viewModel.setQ9MonitoringHabit(option) }
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Question10Screen(assessment: AssessmentState, viewModel: PulseViewModel) {
    Text(
        text = "What would you most like Pulse to help you stay on top of?",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 21.sp),
        color = PulseTextPrimary
    )
    Spacer(modifier = Modifier.height(8.dp))
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = PulseTealContainer
    ) {
        Text(
            text = "Choose up to 2",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = PulseTealPrimary,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
    Spacer(modifier = Modifier.height(20.dp))

    val priorityOptions = listOf(
        "Blood sugar",
        "Blood pressure",
        "Weight",
        "Sleep",
        "Stress / mental wellbeing",
        "Physical activity",
        "Diet",
        "Medication",
        "Existing health condition",
        "General health awareness"
    )

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        priorityOptions.forEach { priority ->
            val isSelected = assessment.q10Priorities.contains(priority)
            MultiSelectChip(
                text = priority,
                isSelected = isSelected,
                onClick = { viewModel.toggleQ10Priority(priority) },
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "${assessment.q10Priorities.size} of 2 priorities selected",
        style = MaterialTheme.typography.bodySmall,
        color = PulseTextMuted
    )
}

/**
 * Page 5 — Profile Generation (Loading / processing state)
 */
@Composable
fun ProfileGenerationScreen(
    uiState: PulseUiState,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_spin")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    Scaffold(
        containerColor = PulseBackground,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(110.dp),
                contentAlignment = Alignment.Center
            ) {
                // Pulsing outer circle
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(PulseTealContainer.copy(alpha = pulseAlpha))
                )
                // Center Icon
                Surface(
                    shape = CircleShape,
                    color = PulseTealPrimary,
                    modifier = Modifier.size(64.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Creating your Pulse Profile…",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                textAlign = TextAlign.Center,
                color = PulseTextPrimary
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Looking at your answers to identify the areas that matter most to you.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = PulseTextSecondary
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Smooth linear progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(PulseSurfaceVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(uiState.generationProgress)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(3.dp))
                        .background(PulseTealPrimary)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Personalizing health-awareness insights",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                color = PulseTextMuted
            )
        }
    }
}
