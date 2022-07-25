# SadaPay Case Study

## Objective
The goal of this assignment is to build a simple single-screen app that shows the current
trending Github repositories fetched from a public API. The design and business
specifications have been provided below.
We have deliberately kept the app simple enough for everyone to attempt it but we are keen to see the approach you take to solve it. You have the freedom to give your best to it and demonstrate your skills for us to evaluate you better.
You should approach this problem as if you are building an MVP app for our production users
- the process you follow and the things you should consider should reflect how you would
  approach this solution if you were to be working for us on something we are going to release
  to customers.

## API Gateway
Github Api service that helps in fetching data from their servers.[Github Repositories](
https://api.github.com/search/repositories?q=language=+sort:stars).

## Structure
###### Application have two modules; app and network
App Module contains following:
- SadaPayCaseStudy.kt (application class)
- HomeActivity.kt (only activity)
- FlowUseCase.kt
- BaseViewHolder (recyclerView ViewHolder)
- Managers package
- Home feature package
- All Ui components

Network Module contains following:
- OkHttpClientProvider
- RetrofitProvider
- Helper classes for network stuff

###### Home module follows clean architecture with packages:
- data (data related stuff)
- domain (business logic)
- presentation (ui related stuff)

## Data Layer
- Service
- Response POJOs
- Ui Models
- Repository
- DataSource Provider

## Presentation Layer
- Bindings
- Fragment
- ViewModel
- Adapter + ViewHolder
- LoadState Adapter + ViewHolder

## Domain Layer
- UseCase

## DI
Hilt is used for dependency injection, following classes are used for DI related stuff.
- HomeModule
- ManagersModule
- NetworkModule

## Testing
This case study includes unit-tests for providers, managers, use-case, repository, viewModel and Ui tests for activity and fragment
Frameworks used for testing
- Mockito
- JUnit
- Espresso etc.

## Explanation
This case-study is responsible for showing top-starred repositories of programming languages. 
This case-study is implemented with a single activity architecture that contains navigation graph with a
single fragment (HomeFragment). This fragment is part of home feature that's responsible for showing
list of repositories in RecyclerView with Paging3 library in case of success response from service that
is in data layer. Error screen shown if any sort of error occurs from network. Shimmer layout is used
to show while api is calling. Each cell shows repo name, description, language, stars count and
watches count. Dark mode is also integrated in this application.

## License
[MIT](https://github.com/Syedovaiss/sada-pay-case-study/blob/master/LICENSE)
