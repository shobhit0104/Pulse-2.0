package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.Doctor
import com.example.ui.theme.*
import com.example.viewmodel.PulseUiState
import com.example.viewmodel.PulseViewModel

/**
 * Page 11 — Privacy Modal / Privacy Center
 */
@Composable
fun PrivacyCenterModal(
    onDismiss: () -> Unit
) {
    var healthInfoPrivate by remember { mutableStateOf(true) }
    var doctorSharingOnChoice by remember { mutableStateOf(true) }
    var familySharingEnabled by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
            modifier = Modifier.fillMaxWidth().testTag("privacy_center_modal")
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Your Health. Your Control.",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = PulseTextPrimary
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = PulseTextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Pulse uses your information to personalize your health-awareness experience.",
                    style = MaterialTheme.typography.bodySmall,
                    color = PulseTextSecondary
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Privacy Switch Item 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Health information",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = PulseTextPrimary
                        )
                        Text(
                            text = "Stored privately on your device",
                            style = MaterialTheme.typography.bodySmall,
                            color = PulseTextSecondary
                        )
                    }
                    Switch(
                        checked = healthInfoPrivate,
                        onCheckedChange = { healthInfoPrivate = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PulseTealPrimary)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = PulseDivider, thickness = 0.8.dp)
                Spacer(modifier = Modifier.height(14.dp))

                // Privacy Switch Item 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Doctor sharing",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = PulseTextPrimary
                        )
                        Text(
                            text = "Only when you explicitly choose",
                            style = MaterialTheme.typography.bodySmall,
                            color = PulseTextSecondary
                        )
                    }
                    Switch(
                        checked = doctorSharingOnChoice,
                        onCheckedChange = { doctorSharingOnChoice = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PulseTealPrimary)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = PulseDivider, thickness = 0.8.dp)
                Spacer(modifier = Modifier.height(14.dp))

                // Privacy Switch Item 3
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Family sharing",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = PulseTextPrimary
                        )
                        Text(
                            text = "Not enabled",
                            style = MaterialTheme.typography.bodySmall,
                            color = PulseTextSecondary
                        )
                    }
                    Switch(
                        checked = familySharingEnabled,
                        onCheckedChange = { familySharingEnabled = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PulseTealPrimary)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                PulsePrimaryButton(text = "Save Preferences", onClick = onDismiss)
            }
        }
    }
}

/**
 * Page 12 — Doctor Profile Modal
 */
@Composable
fun DoctorProfileModal(
    doctor: Doctor,
    onClose: () -> Unit,
    onBookConsultation: (String) -> Unit
) {
    Dialog(onDismissRequest = onClose) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
            modifier = Modifier.fillMaxWidth().testTag("doctor_profile_modal")
        ) {
            Column(
                modifier = Modifier
                    .padding(22.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Doctor Profile",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = PulseTextSecondary
                    )
                    IconButton(onClick = onClose, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = PulseTextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = PulseTealContainer,
                        modifier = Modifier.size(60.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.MedicalServices,
                                contentDescription = null,
                                tint = PulseTealPrimary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = doctor.name,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = PulseTextPrimary
                        )
                        Text(
                            text = doctor.specialty,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = PulseTealPrimary
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFD97706),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${doctor.rating} · ${doctor.experience}",
                                style = MaterialTheme.typography.bodySmall,
                                color = PulseTextSecondary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = PulseSurfaceVariant
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "“${doctor.recommendationReason}”",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = PulseTextPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Based on the information you provided, this professional may be relevant to your health-awareness journey.",
                            style = MaterialTheme.typography.bodySmall,
                            color = PulseTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                MedicalDisclaimer()

                Spacer(modifier = Modifier.height(20.dp))

                PulsePrimaryButton(
                    text = "Book Consultation",
                    onClick = { onBookConsultation(doctor.name) },
                    modifier = Modifier.fillMaxWidth().testTag("modal_book_btn")
                )

                Spacer(modifier = Modifier.height(10.dp))

                PulseSecondaryButton(
                    text = "Close",
                    onClick = onClose,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

/**
 * 30-Day Doctor Summary Modal
 */
@Composable
fun DoctorSummaryModal(
    onDismiss: () -> Unit,
    onShareWithDoctor: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
            modifier = Modifier.fillMaxWidth().testTag("doctor_summary_modal")
        ) {
            Column(
                modifier = Modifier
                    .padding(22.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Your 30-Day Health Summary",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = PulseTextPrimary
                        )
                        Text(
                            text = "Prepared for Ananya · Last 30 Days",
                            style = MaterialTheme.typography.bodySmall,
                            color = PulseTextSecondary
                        )
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = PulseTextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Section 1: Key trends
                Text(
                    text = "Key Trends",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = PulseTealPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = PulseSurfaceVariant
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = "• Sleep averaged 6.4h (improving from 5.8h baseline)", style = MaterialTheme.typography.bodySmall, color = PulseTextPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "• 4,850 minutes of recorded walking & activity this month", style = MaterialTheme.typography.bodySmall, color = PulseTextPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "• Medication adherence at 89% (27 of 30 days)", style = MaterialTheme.typography.bodySmall, color = PulseTextPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Section 2: Recent changes
                Text(
                    text = "Recent Changes",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = PulseTealPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = PulseSurfaceVariant
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = "• ↑ Sleep duration improved by 14%", style = MaterialTheme.typography.bodySmall, color = PulseTextPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "• ↑ Activity consistency improved across weekdays", style = MaterialTheme.typography.bodySmall, color = PulseTextPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "• → Resting blood pressure stable (128 / 82 mmHg)", style = MaterialTheme.typography.bodySmall, color = PulseTextPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Section 3: Questions to discuss with your doctor
                Text(
                    text = "Questions to Discuss With Your Doctor",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = PulseTealPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = PulseTealContainer.copy(alpha = 0.5f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PulseTealPrimary.copy(alpha = 0.2f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = "“My sleep has been inconsistent during work deadlines.”", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium), color = PulseTextPrimary)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "“I missed medication 4 times this month.”", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium), color = PulseTextPrimary)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "“My average activity increased to 32 min/day.”", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium), color = PulseTextPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                MedicalDisclaimer()

                Spacer(modifier = Modifier.height(20.dp))
                PulsePrimaryButton(
                    text = "Share with Doctor",
                    onClick = onShareWithDoctor,
                    modifier = Modifier.fillMaxWidth().testTag("share_summary_btn")
                )
            }
        }
    }
}

/**
 * Report Upload Modal
 */
@Composable
fun ReportUploadModal(
    onDismiss: () -> Unit,
    onUploadSelected: (String) -> Unit
) {
    val reportTypes = listOf("Blood test", "Blood sugar", "Blood pressure", "Thyroid", "Other")
    var selectedType by remember { mutableStateOf("Blood test") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
            modifier = Modifier.fillMaxWidth().testTag("report_upload_modal")
        ) {
            Column(
                modifier = Modifier
                    .padding(22.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Upload Health Report",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = PulseTextPrimary
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = PulseTextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Attach documents to keep your timeline organized for doctor reviews.",
                    style = MaterialTheme.typography.bodySmall,
                    color = PulseTextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Select Report Category",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = PulseTextPrimary
                )
                Spacer(modifier = Modifier.height(10.dp))

                reportTypes.forEach { type ->
                    SelectionCard(
                        title = type,
                        isSelected = selectedType == type,
                        onClick = { selectedType = type }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = PulseSurfaceVariant,
                    border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.UploadFile,
                            contentDescription = null,
                            tint = PulseTealPrimary,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "sample_report_oct2026.pdf",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                            color = PulseTextPrimary
                        )
                        Text(
                            text = "Ready to upload (1.2 MB)",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = PulseTextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
                PulsePrimaryButton(
                    text = "Choose file & Upload",
                    onClick = { onUploadSelected(selectedType) },
                    modifier = Modifier.fillMaxWidth().testTag("confirm_upload_btn")
                )
            }
        }
    }
}

/**
 * Page 13 — Reusable Safety / Attention Alert Dialog
 */
@Composable
fun SafetyAttentionDialog(
    onDismiss: () -> Unit,
    onContactDoctor: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
            modifier = Modifier.fillMaxWidth().testTag("safety_alert_dialog")
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = CircleShape,
                    color = PulseAlertContainer,
                    modifier = Modifier.size(60.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.WarningAmber,
                            contentDescription = null,
                            tint = PulseAlert,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Something needs attention",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    textAlign = TextAlign.Center,
                    color = PulseAlert
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Your recent health reading may require professional attention. Pulse does not provide clinical diagnoses or adjust treatment plans.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = PulseTextSecondary
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        onContactDoctor()
                        onDismiss()
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PulseTealPrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Contact my doctor", color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "View my readings", color = PulseTextPrimary)
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Get urgent help", color = PulseAlert, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/**
 * Mock Photo/Screenshot Upload Dialog for Pulse 60
 */
@Composable
fun MockUploadDialog(
    title: String,
    description: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = PulseSurface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = CircleShape,
                    color = PulseTealContainer,
                    modifier = Modifier.size(54.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Outlined.PhotoCamera,
                            contentDescription = null,
                            tint = PulseTealPrimary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    color = PulseTextPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = PulseTextSecondary
                )

                Spacer(modifier = Modifier.height(20.dp))

                PulsePrimaryButton(
                    text = "Attach & Confirm",
                    onClick = onConfirm,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss) {
                    Text(text = "Cancel", color = PulseTextMuted)
                }
            }
        }
    }
}
