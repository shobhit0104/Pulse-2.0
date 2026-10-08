package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun PulsePrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PulseTealPrimary,
            contentColor = Color.White,
            disabledContainerColor = PulseTealPrimary.copy(alpha = 0.4f),
            disabledContentColor = Color.White.copy(alpha = 0.7f)
        ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 52.dp)
            .testTag("primary_btn_${text.replace(" ", "_").lowercase()}")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            leadingIcon?.invoke()
            if (leadingIcon != null) Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 15.sp),
                color = Color.White
            )
            if (trailingIcon != null) {
                Spacer(modifier = Modifier.width(8.dp))
                trailingIcon.invoke()
            }
        }
    }
}

@Composable
fun PulseSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.2.dp, PulseDivider),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = PulseTextPrimary
        ),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 13.dp),
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp)
            .testTag("secondary_btn_${text.replace(" ", "_").lowercase()}")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            leadingIcon?.invoke()
            if (leadingIcon != null) Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp),
                color = PulseTextPrimary
            )
        }
    }
}

@Composable
fun SelectionCard(
    title: String,
    subtitle: String? = null,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null
) {
    val borderColor = if (isSelected) PulseTealPrimary else PulseDivider
    val backgroundColor = if (isSelected) PulseTealContainer.copy(alpha = 0.45f) else PulseSurface

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = backgroundColor,
        border = androidx.compose.foundation.BorderStroke(if (isSelected) 1.8.dp else 1.dp, borderColor),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .testTag("selection_card_${title.take(15).replace(" ", "_").lowercase()}")
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 15.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Box(modifier = Modifier.padding(end = 14.dp)) {
                    icon()
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
                    ),
                    color = PulseTextPrimary
                )
                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = PulseTextSecondary
                    )
                }
            }

            // Radio indicator
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(CircleShape)
                    .border(
                        width = if (isSelected) 6.dp else 1.5.dp,
                        color = if (isSelected) PulseTealPrimary else PulseTextMuted,
                        shape = CircleShape
                    )
                    .background(if (isSelected) Color.White else Color.Transparent)
            )
        }
    }
}

@Composable
fun MultiSelectChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) PulseTealPrimary else PulseSurfaceVariant
    val textColor = if (isSelected) Color.White else PulseTextPrimary
    val borderColor = if (isSelected) PulseTealPrimary else PulseDivider

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, borderColor, RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 9.dp)
            .testTag("chip_${text.replace(" ", "_").lowercase()}"),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    fontSize = 13.sp
                ),
                color = textColor
            )
        }
    }
}

@Composable
fun PulseProgressBar(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier
) {
    val progress = (currentStep.toFloat() / totalSteps.toFloat()).coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(targetValue = progress, animationSpec = tween(300), label = "prog")

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Question $currentStep of $totalSteps",
                style = MaterialTheme.typography.labelMedium,
                color = PulseTextSecondary
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = PulseTealPrimary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(PulseSurfaceVariant)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(3.dp))
                    .background(PulseTealPrimary)
            )
        }
    }
}

@Composable
fun ScoreDonutChart(
    score: Int,
    maxScore: Int = 100,
    modifier: Modifier = Modifier
) {
    val animatedScore by animateFloatAsState(
        targetValue = score.toFloat() / maxScore.toFloat(),
        animationSpec = tween(durationMillis = 800),
        label = "score_anim"
    )

    Box(
        modifier = modifier.size(170.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 14.dp.toPx()
            val diameter = size.minDimension - strokeWidth
            val topLeft = Offset((size.width - diameter) / 2, (size.height - diameter) / 2)
            val arcSize = Size(diameter, diameter)

            // Background circle track
            drawArc(
                color = Color(0xFFE2EBE9),
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Active progress arc
            drawArc(
                color = PulseTealPrimary,
                startAngle = 135f,
                sweepAngle = 270f * animatedScore,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "PULSE SCORE",
                style = MaterialTheme.typography.labelSmall.copy(
                    letterSpacing = 1.2.sp,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                ),
                color = PulseTextMuted
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "$score",
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 42.sp),
                    color = PulseTextPrimary
                )
                Text(
                    text = "/$maxScore",
                    style = MaterialTheme.typography.titleMedium,
                    color = PulseTextMuted,
                    modifier = Modifier.padding(bottom = 6.dp, start = 2.dp)
                )
            }
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = PulseGreenLight,
                modifier = Modifier.padding(top = 2.dp)
            ) {
                Text(
                    text = "↑ 6 points",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = PulseGreenDark,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
fun DimensionProgressBar(
    dimension: HealthDimension,
    modifier: Modifier = Modifier
) {
    val barColor = when (dimension.statusTone) {
        DimensionTone.POSITIVE -> PulseGreen
        DimensionTone.NEUTRAL -> PulseTealLight
        DimensionTone.ATTENTION -> PulseWarning
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = dimension.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                    color = PulseTextPrimary
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${dimension.score}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                    color = PulseTextPrimary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = when (dimension.statusTone) {
                        DimensionTone.POSITIVE -> PulseGreenLight
                        DimensionTone.NEUTRAL -> PulseSurfaceVariant
                        DimensionTone.ATTENTION -> PulseWarningContainer
                    }
                ) {
                    Text(
                        text = dimension.status,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = when (dimension.statusTone) {
                            DimensionTone.POSITIVE -> PulseGreenDark
                            DimensionTone.NEUTRAL -> PulseTextSecondary
                            DimensionTone.ATTENTION -> Color(0xFF92400E)
                        },
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(PulseSurfaceVariant)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(dimension.score / 100f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .background(barColor)
            )
        }
    }
}

@Composable
fun MedicalDisclaimer(
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = PulseSurfaceVariant,
        border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = PulseTextMuted,
                modifier = Modifier
                    .size(18.dp)
                    .padding(top = 2.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Pulse provides health awareness and wellbeing support. It does not diagnose conditions or replace professional medical advice.",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp, lineHeight = 16.sp),
                color = PulseTextSecondary
            )
        }
    }
}

/**
 * 7-Day Graphical Progress Component for Pulse 60
 * Visualizes Mon-Sun completion for Movement, Meals, Wellbeing
 * Exactly adheres to hackathon specification data and visual clarity.
 */
@Composable
fun SevenDayHabitMatrix(
    habitDays: List<DayHabitRecord>,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PulseSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Your last 7 days",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = PulseTextPrimary
                    )
                    Text(
                        text = "See how consistently you’ve been taking care of yourself.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PulseTextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Legend
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(9.dp)
                            .clip(CircleShape)
                            .background(PulseTealPrimary)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Movement", style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp), color = PulseTextSecondary)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(9.dp)
                            .clip(CircleShape)
                            .background(PulseGreen)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Meals", style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp), color = PulseTextSecondary)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(9.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF0284C7)) // Soft medical blue
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Wellbeing", style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp), color = PulseTextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 7 Days Columns
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                habitDays.forEach { record ->
                    val isBest = record.dayName == "Thursday"
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = record.dayShort,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isBest) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.sp
                            ),
                            color = if (isBest) PulseTealPrimary else PulseTextSecondary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Column card container representing the 3 daily metrics
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isBest) PulseTealContainer.copy(alpha = 0.5f) else PulseSurfaceVariant,
                            border = if (isBest) androidx.compose.foundation.BorderStroke(1.2.dp, PulseTealPrimary.copy(alpha = 0.5f)) else null,
                            modifier = Modifier
                                .width(34.dp)
                                .padding(vertical = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                MetricCheckDot(
                                    isCompleted = record.movementCompleted,
                                    activeColor = PulseTealPrimary
                                )
                                MetricCheckDot(
                                    isCompleted = record.mealsCompleted,
                                    activeColor = PulseGreen
                                )
                                MetricCheckDot(
                                    isCompleted = record.wellbeingCompleted,
                                    activeColor = Color(0xFF0284C7)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Status badge (✓, ◐, ○)
                        Text(
                            text = when {
                                record.isFullyCompleted -> "✓"
                                record.isPartiallyCompleted -> "◐"
                                else -> "○"
                            },
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            ),
                            color = when {
                                record.isFullyCompleted -> PulseGreenDark
                                record.isPartiallyCompleted -> PulseWarning
                                else -> PulseTextMuted
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = PulseDivider, thickness = 0.8.dp)
            Spacer(modifier = Modifier.height(12.dp))

            // Summary notes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = PulseGreenLight,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = PulseGreenDark,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "6 of 7 days completed",
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 13.5.sp),
                            color = PulseTextPrimary
                        )
                        Text(
                            text = "Your consistency is improving.",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                            color = PulseGreenDark
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Best day: Thursday",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold, fontSize = 11.sp),
                        color = PulseTealPrimary
                    )
                    Text(
                        text = "Strongest habit: Movement",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = PulseTextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricCheckDot(
    isCompleted: Boolean,
    activeColor: Color
) {
    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(if (isCompleted) activeColor else PulseDivider)
            .border(
                width = 0.5.dp,
                color = if (isCompleted) activeColor.copy(alpha = 0.4f) else Color.Transparent,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isCompleted) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(11.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }
    }
}

/**
 * Clean Canvas-drawn Line Chart for 30-Day Pulse Score
 * Week 1: 72 -> Week 2: 74 -> Week 3: 76 -> Week 4: 78
 */
@Composable
fun PulseTrendLineChart(
    modifier: Modifier = Modifier
) {
    val weeks = listOf("Week 1", "Week 2", "Week 3", "Week 4")
    val scores = listOf(72, 74, 76, 78)

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PulseSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Pulse Score Trend",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = PulseTextPrimary
                    )
                    Text(
                        text = "Your Pulse has improved over the last 30 days.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PulseTextSecondary
                    )
                }
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = PulseGreenLight
                ) {
                    Text(
                        text = "+6 points",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = PulseGreenDark,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Canvas Line Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val paddingLeft = 16.dp.toPx()
                    val paddingRight = 16.dp.toPx()
                    val paddingTop = 14.dp.toPx()
                    val paddingBottom = 16.dp.toPx()

                    val chartWidth = size.width - paddingLeft - paddingRight
                    val chartHeight = size.height - paddingTop - paddingBottom

                    val minScore = 70f
                    val maxScore = 80f

                    val points = scores.mapIndexed { index, score ->
                        val x = paddingLeft + (index.toFloat() / (scores.size - 1)) * chartWidth
                        val normalizedY = (score - minScore) / (maxScore - minScore)
                        val y = size.height - paddingBottom - (normalizedY * chartHeight)
                        Offset(x, y)
                    }

                    // Background grid lines
                    val gridSteps = 3
                    for (i in 0..gridSteps) {
                        val gy = paddingTop + (i.toFloat() / gridSteps) * chartHeight
                        drawLine(
                            color = Color(0xFFEFF3F2),
                            start = Offset(paddingLeft, gy),
                            end = Offset(size.width - paddingRight, gy),
                            strokeWidth = 1.dp.toPx()
                        )
                    }

                    // Smooth Path
                    val path = Path().apply {
                        moveTo(points[0].x, points[0].y)
                        for (i in 1 until points.size) {
                            val pPrev = points[i - 1]
                            val pCurr = points[i]
                            val cX = (pPrev.x + pCurr.x) / 2
                            cubicTo(cX, pPrev.y, cX, pCurr.y, pCurr.x, pCurr.y)
                        }
                    }

                    drawPath(
                        path = path,
                        color = PulseTealPrimary,
                        style = Stroke(width = 3.5.dp.toPx(), cap = StrokeCap.Round)
                    )

                    // Draw points
                    points.forEachIndexed { idx, pt ->
                        val isLast = idx == points.size - 1
                        drawCircle(
                            color = Color.White,
                            radius = if (isLast) 7.dp.toPx() else 5.dp.toPx(),
                            center = pt
                        )
                        drawCircle(
                            color = if (isLast) PulseGreen else PulseTealPrimary,
                            radius = if (isLast) 5.dp.toPx() else 3.5.dp.toPx(),
                            center = pt
                        )
                    }
                }
            }

            // Labels below chart
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                weeks.forEachIndexed { i, wk ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = wk,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = PulseTextMuted
                        )
                        Text(
                            text = "${scores[i]}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (i == 3) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.sp
                            ),
                            color = if (i == 3) PulseTealPrimary else PulseTextSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DoctorCard(
    doctor: Doctor,
    onViewDoctor: () -> Unit,
    onBookConsultation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PulseSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = PulseTealContainer,
                    modifier = Modifier.size(46.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.MedicalServices,
                            contentDescription = null,
                            tint = PulseTealPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = doctor.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = PulseTextPrimary
                    )
                    Text(
                        text = doctor.specialty,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = PulseTealPrimary
                    )
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = PulseWarningContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${doctor.rating}",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.5.sp),
                            color = Color(0xFF92400E)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.WorkOutline,
                    contentDescription = null,
                    tint = PulseTextMuted,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = doctor.experience,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = PulseTextSecondary
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = PulseSurfaceVariant
            ) {
                Text(
                    text = "“${doctor.recommendationReason}”",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
                    color = PulseTextSecondary,
                    modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onViewDoctor,
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = PulseTextPrimary),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("view_doc_${doctor.id}")
                ) {
                    Text(text = "View Doctor", style = MaterialTheme.typography.labelMedium.copy(fontSize = 13.sp))
                }

                Button(
                    onClick = onBookConsultation,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PulseTealPrimary),
                    modifier = Modifier
                        .weight(1.2f)
                        .height(44.dp)
                        .testTag("book_doc_${doctor.id}")
                ) {
                    Text(text = "Book Consultation", style = MaterialTheme.typography.labelMedium.copy(fontSize = 13.sp, color = Color.White))
                }
            }
        }
    }
}

/**
 * Lightweight Reusable Safety / Attention Alert Banner & Modal
 */
@Composable
fun SafetyAttentionBanner(
    onContactDoctor: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PulseAlertContainer),
        border = androidx.compose.foundation.BorderStroke(1.dp, PulseAlert.copy(alpha = 0.35f)),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = PulseAlert.copy(alpha = 0.15f),
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.WarningAmber,
                        contentDescription = null,
                        tint = PulseAlert,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Something needs attention",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
                    color = PulseAlert
                )
                Text(
                    text = "Your recent health reading may benefit from professional guidance.",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = PulseTextPrimary
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            TextButton(onClick = onContactDoctor) {
                Text(
                    text = "Details",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = PulseAlert
                )
            }
        }
    }
}
