package com.banquemisr.login

import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.banquemisr.login.ui.theme.BanqueMisrLoginTheme
import com.banquemisr.login.ui.theme.Redd
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        val savedLang = LocaleManager.getSelectedLanguage(this)
        LocaleManager.setLocale(this, savedLang)

        setContent {
            BanqueMisrLoginTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Login(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun setAppLanguage(languageTag: String) {
        LocaleManager.setLocale(this, languageTag)
        recreate()
    }

    @Composable
    fun Login(modifier: Modifier = Modifier) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val isLoginEnabled = username.isNotBlank() && password.isNotBlank()



        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.Top
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.bm_icon),
                    contentDescription = stringResource(R.string.bm_icon),
                    modifier = Modifier.padding(top = 64.dp)
                )

                Text(
                    text = stringResource(R.string.Lang),
                    fontSize = 16.sp,
                    color = Redd,
                    modifier = Modifier
                        .padding(top = 64.dp)
                        .clickable {
                            val currentLang = LocaleManager.getSelectedLanguage(this@MainActivity)
                            val newLang = if (currentLang == "ar") "en" else "ar"
                            LocaleManager.setLocale(this@MainActivity, newLang)
                            recreate()
                        }

                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(R.string.username)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.forgot_password),
                textDecoration = TextDecoration.Underline,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 4.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { /* TODO: handle login */ },
                enabled = isLoginEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isLoginEnabled) Redd else Color.LightGray,
                    contentColor = Color.White,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(stringResource(R.string.login))
            }


            Spacer(modifier = Modifier.height(16.dp))

            //  Help Text: "Need help? Contact us" with partial styling
            val needHelpPrefix = stringResource(R.string.need_help_prefix)
            val contactUs = stringResource(R.string.contact_us)

            val annotatedHelpText = buildAnnotatedString {
                append(needHelpPrefix)
                withStyle(
                    style = SpanStyle(
                        color = Redd,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(contactUs)
                }
            }

            Text(
                text = annotatedHelpText,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 4.dp)
            )

            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )


            Spacer(modifier = Modifier.height(10.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconWithLabel(R.drawable.our_products, R.string.products)
                IconWithLabel(R.drawable.exchange_rate, R.string.exchange)
                IconWithLabel(R.drawable.security_tips, R.string.tips)
                IconWithLabel(R.drawable.nearest_branch_or_atm, R.string.nearest)
            }
        }
    }

    @Composable
    fun IconWithLabel(iconRes: Int, labelRes: Int) {
        Column(
            modifier = Modifier
                .width(90.dp)
                .height(110.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                painter = painterResource(id = iconRes),
                contentDescription = stringResource(id = labelRes),
                modifier = Modifier.size(64.dp)
            )

            Text(
                text = stringResource(id = labelRes),
                fontSize = 16.sp,
                lineHeight = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun Preview() {
        Login()
    }
}