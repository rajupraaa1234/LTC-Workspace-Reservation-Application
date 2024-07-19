import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import com.example.ltcworkspacereservationapplication.presentation.utils.HomeTabs
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
internal fun TabSelection(modifier: Modifier, onClick: (HomeTabs) -> Unit) {
    var selectedTab by remember { mutableStateOf(HomeTabs.entries.get(0).text) }
    var parentWidth by remember { mutableStateOf(Spacing.Size_0) }

    BoxWithConstraints(
        modifier = modifier
            .clip(RoundedCornerShape(Spacing.Size_5))
            .background(AppColor.primaryColor)
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                parentWidth = with(coordinates.size.width.dp / 2) { this }
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            HomeTabs.entries.forEachIndexed { index, currentTab ->
                Box(modifier = Modifier.weight(1f)) {
                    TabItem(
                        text = currentTab.text,
                        isSelected = selectedTab == currentTab.text,
                        onClick = {
                            selectedTab = currentTab.text
                            onClick(currentTab)
                        },
                        width = parentWidth / 2,
                    )
                }
            }
        }
    }
}

@Composable
private fun TabItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    width: Dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(width)
            .height(Spacing.Size_48)
            .background(
                color = if (isSelected) Color.Transparent else AppColor.whiteColor,
                shape = RoundedCornerShape(Spacing.Size_5)
            )
            .clickable { onClick() }
            .border(
                width = if (isSelected) Spacing.Size_1 else Spacing.Size_0,
                color = if (isSelected) Color.White else Color.Transparent,
                shape = RoundedCornerShape(Spacing.Size_5)
            )
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}
