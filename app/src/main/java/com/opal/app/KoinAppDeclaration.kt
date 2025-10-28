package com.opal.app

import android.content.Context
import com.opal.app.data.repository.LiveRewardRepository
import com.opal.app.data.repository.LiveUserRepository
import com.opal.app.data.service.LiveAuthService
import com.opal.app.domain.repository.RewardRepository
import com.opal.app.domain.repository.UserRepository
import com.opal.app.domain.service.AuthService
import com.opal.app.presentation.home.HomeViewModel
import com.opal.app.presentation.reward.ReferralViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun koinAppDeclaration(context: Context): KoinAppDeclaration =
    {
        modules(
            module {
                androidContext(context.applicationContext)
                // presentation
                viewModel {
                    HomeViewModel()
                }
                viewModel {
                    ReferralViewModel(get(), get())
                }
                // data
                single<AuthService> { LiveAuthService() }
                single<UserRepository> { LiveUserRepository() }
                single<RewardRepository> { LiveRewardRepository() }
            },
        )
    }

fun koinPreviewDeclaration(context: Context): KoinAppDeclaration =
    {
        modules(
            module {
                androidContext(context.applicationContext)
                // presentation
                viewModel {
                    HomeViewModel()
                }
                viewModel {
                    ReferralViewModel(get(), get())
                }
                // data
                single<AuthService> { LiveAuthService() }
                single<UserRepository> { LiveUserRepository() }
                single<RewardRepository> { LiveRewardRepository() }
            },
        )
    }
