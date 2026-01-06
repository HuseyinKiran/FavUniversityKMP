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
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huseyinkiran.favuniversitykmp.domain.model.City
import com.huseyinkiran.favuniversitykmp.domain.model.University
import com.huseyinkiran.favuniversitykmp.presentation.CityHeader
import com.huseyinkiran.favuniversitykmp.presentation.topAppBarColor
import favuniversity.composeapp.generated.resources.Res
import favuniversity.composeapp.generated.resources.cd_collapse_city
import favuniversity.composeapp.generated.resources.cd_expand_city
import favuniversity.composeapp.generated.resources.university
import org.jetbrains.compose.resources.stringResource

@Composable
fun CityCard(
    city: City,
    universityList: List<University>,
    isCityExpanded: Boolean,
    expandedUniversityIds: Set<Int>,
    onFavoriteToggle: (University) -> Unit,
    onCityExpandToggle: (Int) -> Unit,
    onUniversityExpandToggle: (Int) -> Unit
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (isCityExpanded) 180f else 0f,
        animationSpec = tween(300),
        label = "city_arrow_rotation"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .clickable { onCityExpandToggle(city.id) },
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = if (isCityExpanded) 8.dp else 4.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CityHeader)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = topAppBarColor,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = city.name,
                        color = topAppBarColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "${universityList.size} ${stringResource(Res.string.university)}",
                        color = Color.DarkGray.copy(alpha = 0.9f),
                        fontSize = 12.sp,
                    )
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isCityExpanded) {
                        stringResource(Res.string.cd_collapse_city)
                    } else {
                        stringResource(Res.string.cd_expand_city)
                    },
                    tint = topAppBarColor,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotationAngle)
                )

            }
        }

        AnimatedVisibility(
            visible = isCityExpanded,
            enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                universityList.forEach { university ->
                    UniversityCard(
                        university = university,
                        isExpanded = university.id in expandedUniversityIds,
                        onExpandToggle = { onUniversityExpandToggle(university.id) },
                        onFavoriteToggle = { onFavoriteToggle(university) },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
    }
}