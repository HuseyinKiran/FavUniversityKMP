package com.huseyinkiran.favuniversitykmp.presentation.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Fax
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huseyinkiran.favuniversitykmp.domain.model.University
import com.huseyinkiran.favuniversitykmp.presentation.platform.rememberEmailLauncher
import com.huseyinkiran.favuniversitykmp.presentation.platform.rememberMapLauncher
import com.huseyinkiran.favuniversitykmp.presentation.platform.rememberPhoneLauncher
import com.huseyinkiran.favuniversitykmp.presentation.topAppBarColor
import com.huseyinkiran.favuniversitykmp.presentation.universityCardColor
import favuniversity.composeapp.generated.resources.Res
import favuniversity.composeapp.generated.resources.address
import favuniversity.composeapp.generated.resources.cd_add_to_favorites
import favuniversity.composeapp.generated.resources.cd_collapse_university
import favuniversity.composeapp.generated.resources.cd_expand_university
import favuniversity.composeapp.generated.resources.cd_remove_from_favorites
import favuniversity.composeapp.generated.resources.email
import favuniversity.composeapp.generated.resources.fax
import favuniversity.composeapp.generated.resources.phone
import favuniversity.composeapp.generated.resources.rector
import favuniversity.composeapp.generated.resources.website
import org.jetbrains.compose.resources.stringResource


private val CardBackground = Color(0xFFFAFAFA)
private val DividerColor = Color(0xFFE0E0E0)
private val BadgeBackground = Color(0xFFFCE4EC)
private val BadgeText = Color(0xFFD8226C)

@Composable
fun UniversityCard(
    university: University,
    isExpanded: Boolean,
    onExpandToggle: () -> Unit,
    onFavoriteToggle: () -> Unit,
    modifier: Modifier = Modifier,
    showDeleteInsteadOfFavorite: Boolean = false,
    onDelete: (() -> Unit)? = null
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(300),
        label = "arrow_rotation"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onExpandToggle() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isExpanded) 8.dp else 2.dp
        )
    ) {
        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(universityCardColor)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) {
                        stringResource(Res.string.cd_collapse_university)
                    } else {
                        stringResource(Res.string.cd_expand_university)
                    },
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotationAngle)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = university.name,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                if (showDeleteInsteadOfFavorite && onDelete != null) {
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = stringResource(Res.string.cd_remove_from_favorites),
                            tint = topAppBarColor
                        )
                    }
                } else {
                    IconButton(
                        onClick = onFavoriteToggle,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (university.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (university.isFavorite) {
                                stringResource(Res.string.cd_remove_from_favorites)
                            } else {
                                stringResource(Res.string.cd_add_to_favorites)
                            },
                            tint = if (university.isFavorite) topAppBarColor else Color.Black
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                ) {

                    if (university.universityType.isNotBlank()) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(BadgeBackground)
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = university.universityType,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = BadgeText
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    if (university.rector.isNotBlank()) {
                        InfoRow(
                            icon = RectorIcon,
                            label = stringResource(Res.string.rector),
                            value = university.rector
                        )
                        InfoDivider()
                    }

                    if (university.address.isNotBlank()) {
                        val isAddressValid = university.address != "-"
                        if (isAddressValid) {
                            val mapLauncher = rememberMapLauncher()
                            ActionableInfoRow(
                                icon = AddressIcon,
                                label = stringResource(Res.string.address),
                                value = university.address,
                                onClick = { mapLauncher(university.address) }
                            )
                        } else {
                            InfoRow(
                                icon = AddressIcon,
                                label = stringResource(Res.string.address),
                                value = university.address
                            )
                        }
                        InfoDivider()
                    }

                    if (university.phone.isNotBlank()) {
                        val isPhoneValid = university.phone != "-"
                        if (isPhoneValid) {
                            val phoneLauncher = rememberPhoneLauncher()
                            ActionableInfoRow(
                                icon = PhoneIcon,
                                label = stringResource(Res.string.phone),
                                value = university.phone,
                                onClick = { phoneLauncher(university.phone) }
                            )
                        } else {
                            InfoRow(
                                icon = PhoneIcon,
                                label = stringResource(Res.string.phone),
                                value = university.phone
                            )
                        }
                        InfoDivider()
                    }

                    if (university.fax.isNotBlank()) {
                        InfoRow(
                            icon = FaxIcon,
                            label = stringResource(Res.string.fax),
                            value = university.fax
                        )
                        InfoDivider()
                    }

                    if (university.email.isNotBlank()) {
                        val isEmailValid = university.email != "-"
                        if (isEmailValid) {
                            val emailLauncher = rememberEmailLauncher()
                            ActionableInfoRow(
                                icon = EmailIcon,
                                label = stringResource(Res.string.email),
                                value = university.email,
                                onClick = { emailLauncher(university.email) }
                            )
                        } else {
                            InfoRow(
                                icon = EmailIcon,
                                label = stringResource(Res.string.email),
                                value = university.email
                            )
                        }
                        InfoDivider()
                    }

                    if (university.website.isNotBlank()) {
                        val isWebsiteValid = university.website != "-"
                        if (isWebsiteValid) {
                            ClickableInfoRow(
                                icon = WebIcon,
                                label = stringResource(Res.string.website),
                                value = university.website.removePrefix("https://")
                                    .removePrefix("http://"),
                                uri = if (university.website.startsWith("http")) university.website else "https://${university.website}"
                            )
                        } else {
                            InfoRow(
                                icon = WebIcon,
                                label = stringResource(Res.string.website),
                                value = university.website
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier.size(width = 20.dp, height = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = topAppBarColor,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                fontSize = 11.sp,
                color = topAppBarColor,
                fontWeight = FontWeight.Medium,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun ClickableInfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    uri: String,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                try {
                    uriHandler.openUri(uri)
                } catch (_: Exception) {
                }
            },
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier.size(width = 20.dp, height = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = topAppBarColor,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                fontSize = 11.sp,
                color = topAppBarColor,
                fontWeight = FontWeight.Medium,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontSize = 14.sp,
                color = Color.Black,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
private fun ActionableInfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier.size(width = 20.dp, height = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = topAppBarColor,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                fontSize = 11.sp,
                color = topAppBarColor,
                fontWeight = FontWeight.Medium,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontSize = 14.sp,
                color = Color.Black,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
private fun InfoDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(start = 32.dp),
        thickness = 0.5.dp,
        color = DividerColor
    )
}

private val FaxIcon = Icons.Outlined.Fax
private val WebIcon = Icons.Outlined.Language
private val RectorIcon = Icons.Default.Person
private val AddressIcon = Icons.Default.LocationOn
private val PhoneIcon = Icons.Default.Phone
private val EmailIcon = Icons.Default.Email