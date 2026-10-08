package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.PulsePrimaryButton
import com.example.ui.components.PulseSecondaryButton
import com.example.ui.theme.*
import com.example.viewmodel.PulseUiState
import com.example.viewmodel.PulseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    uiState: PulseUiState,
    viewModel: PulseViewModel,
    modifier: Modifier = Modifier
) {
    var gender by remember { mutableStateOf("Female") }
    var state by remember { mutableStateOf("Karnataka") }
    var email by remember { mutableStateOf("ananya@example.com") }

    Scaffold(
        containerColor = PulseBackground,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Pulse Logo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(PulseGreen)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "pulse",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-1).sp
                    ),
                    color = PulseTealPrimary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Headline
            Text(
                text = "Take control of your health with Pulse.",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    lineHeight = 30.sp
                ),
                textAlign = TextAlign.Center,
                color = PulseTextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Supporting text
            Text(
                text = "Build your personal health profile and stay aware of what matters most.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = PulseTextSecondary
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Registration Form Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PulseSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Full Name
                    OutlinedTextField(
                        value = uiState.registrationNameInput,
                        onValueChange = { viewModel.updateRegistrationName(it) },
                        label = { Text("Full Name") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PulseTealPrimary,
                            unfocusedBorderColor = PulseDivider,
                            focusedLabelColor = PulseTealPrimary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().testTag("reg_full_name")
                    )

                    // Age & Gender in row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = uiState.registrationAgeInput,
                            onValueChange = { viewModel.updateRegistrationAge(it) },
                            label = { Text("Age") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PulseTealPrimary,
                                unfocusedBorderColor = PulseDivider
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f).testTag("reg_age")
                        )

                        OutlinedTextField(
                            value = gender,
                            onValueChange = { gender = it },
                            label = { Text("Gender") },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PulseTealPrimary,
                                unfocusedBorderColor = PulseDivider
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1.4f).testTag("reg_gender")
                        )
                    }

                    // State & City
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = state,
                            onValueChange = { state = it },
                            label = { Text("State") },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PulseTealPrimary,
                                unfocusedBorderColor = PulseDivider
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f).testTag("reg_state")
                        )

                        OutlinedTextField(
                            value = uiState.registrationCityInput,
                            onValueChange = { viewModel.updateRegistrationCity(it) },
                            label = { Text("City") },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PulseTealPrimary,
                                unfocusedBorderColor = PulseDivider
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f).testTag("reg_city")
                        )
                    }

                    // Phone Number
                    OutlinedTextField(
                        value = uiState.registrationPhoneInput,
                        onValueChange = { viewModel.updateRegistrationPhone(it) },
                        label = { Text("Phone Number") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PulseTealPrimary,
                            unfocusedBorderColor = PulseDivider
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().testTag("reg_phone")
                    )

                    // Email ID (Optional)
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email ID (Optional)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PulseTealPrimary,
                            unfocusedBorderColor = PulseDivider
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().testTag("reg_email")
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Primary CTA
                    PulsePrimaryButton(
                        text = "Register",
                        onClick = { viewModel.onRegisterClicked() },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Privacy notice
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = PulseTextMuted,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Your health information is private and protected.",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                            color = PulseTextMuted,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Secondary option: Already have an account? Log in
            TextButton(
                onClick = { viewModel.onRegisterClicked() },
                modifier = Modifier.testTag("already_account_login")
            ) {
                Text(
                    text = "Already have an account? Log in",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                    color = PulseTealPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
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
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Header Icon
            Surface(
                shape = CircleShape,
                color = PulseTealContainer,
                modifier = Modifier.size(68.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.Security,
                        contentDescription = null,
                        tint = PulseTealPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Headline
            Text(
                text = "Verify your mobile number",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                textAlign = TextAlign.Center,
                color = PulseTextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Supporting text
            Text(
                text = "We’ve sent a 6-digit OTP to your registered mobile number ${uiState.registrationPhoneInput}.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = PulseTextSecondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            // OTP Input Box
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = PulseSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, PulseDivider),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Enter 6-digit Code",
                        style = MaterialTheme.typography.labelMedium,
                        color = PulseTextSecondary
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // 6-digit styled OTP display
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val digits = uiState.otpInput.padEnd(6, ' ').take(6)
                        digits.forEachIndexed { index, char ->
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(PulseSurfaceVariant)
                                    .border(
                                        width = if (char != ' ') 1.5.dp else 1.dp,
                                        color = if (char != ' ') PulseTealPrimary else PulseDivider,
                                        shape = RoundedCornerShape(10.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (char != ' ') char.toString() else "•",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    ),
                                    color = PulseTextPrimary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Primary CTA
                    PulsePrimaryButton(
                        text = "Verify & Continue",
                        onClick = { viewModel.onVerifyOtpClicked() },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Resend & Countdown
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Resend OTP in ",
                            style = MaterialTheme.typography.bodySmall,
                            color = PulseTextMuted
                        )
                        Text(
                            text = "00:48",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                            color = PulseTealPrimary
                        )
                    }

                    TextButton(
                        onClick = { /* mock resend */ },
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = "Resend OTP",
                            style = MaterialTheme.typography.labelMedium,
                            color = PulseTealPrimary
                        )
                    }
                }
            }
        }
    }
}
