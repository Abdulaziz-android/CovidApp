package com.abdulaziz.covidapp.utils.local_resurces

import com.abdulaziz.covidapp.data.model.Article

object ArticlesData {

    fun getData(): ArrayList<Article> {
        return arrayListOf(
            Article(
                title = "Coronavirus disease (COVID-19)",
                description = "Coronavirus disease (COVID-19) is an infectious disease caused by the SARS-CoV-2 virus.",
                content = "Most people infected with the virus will experience mild to moderate respiratory illness and recover without requiring special treatment." +
                        " However, some will become seriously ill and require medical attention. Older people and those with underlying medical conditions like cardiovascular disease," +
                        " diabetes, chronic respiratory disease, or cancer are more likely to develop serious illness. Anyone can get sick with COVID-19 and become seriously ill or die at any age. \n\n" +
                        "The best way to prevent and slow down transmission is to be well informed about the disease and how the virus spreads. Protect yourself and others from infection by staying at least 1 metre apart from others, " +
                        "wearing a properly fitted mask, and washing your hands or using an alcohol-based rub frequently. Get vaccinated when it’s your turn and follow local guidance.",
                image_url = "https://www.who.int/images/default-source/mca/mca-covid-19/coronavirus-2.tmb-1024v.jpg?Culture=en&sfvrsn=4dba955c_6"
            ),
            Article(
                title = "Developing a framework for evaluating new COVID-19 vaccines",
                description = "The WHO R&D Blueprint organized a consultation to discuss emerging evidence towards the establishment of correlates of protection for COVID-19 vaccines. ",
                content = "There is a global need to increase the supply of vaccines that likely meet the WHO TPP criteria for effectiveness against severe disease.\n\n" +
                        "WHO is considering the development of a framework to help evaluate new vaccines, against currently circulating variants or for pan-sarbecovirus vaccines. This may also help inform researchers and developers decisions on how to generate additional data for the assessment of new vaccines.",
                image_url = "https://images.assetsdelivery.com/compings_v2/virtis/virtis2102/virtis210200038.jpg"
            ),
            Article(
                title = "Mental Health and COVID-19",
                description = "Early evidence of the pandemic’s impact: Scientific brief",
                content = "The COVID-19 pandemic has had a severe impact on the mental health and wellbeing of people around the world while also raising concerns of increased suicidal behaviour. In addition access to mental health services has been severely impeded. However, no comprehensive summary of the current data on these impacts has until now been made widely available.\n" +
                        "\n" +
                        "This scientific brief is based on evidence from research commissioned by WHO, including an umbrella review of systematic reviews and meta-analyses and an update to a living systematic review. Informed by these reviews, the scientific brief provides a comprehensive overview of current evidence about:\n" +
                        "\n" +
                        "1. The impact of the COVID-19 pandemic on the prevalence of mental health symptoms and mental disorders\n" +
                        "2. The impact of the COVID-19 pandemic on prevalence of suicidal thoughts and behaviours\n" +
                        "3. The risk of infection, severe illness and death from COVID-19 for people living with mental disorders\n" +
                        "4. The impact of the COVID-19 pandemic on mental health services\n" +
                        "5. The effectiveness of psychological interventions adapted to the COVID-19 pandemic to prevent or reduce mental health problems and/or maintain access to mental health services" ,
                image_url = "https://healthsci.mcmaster.ca/images/default-source/news/groups/education/departments/medicine/demystifying-medicine/covid-19-and-mental-health.jpg?sfvrsn=2a2f52f2_4"
            ),
            Article(
                title = "COVID-19 pandemic triggers 25% increase in prevalence of anxiety and depression worldwide",
                description = "Wake-up call to all countries to step up mental health services and support",
                content = "In the first year of the COVID-19 pandemic, global prevalence of anxiety and depression increased by a massive 25%, according to a scientific brief released by the World Health Organization (WHO) today. The brief also highlights who has been most affected and summarizes the effect of the pandemic on " +
                        "the availability of mental health services and how this has changed during the pandemic.\n" +
                        "\n" +
                        "Concerns about potential increases in mental health conditions had already prompted 90% of countries surveyed to include mental health and psychosocial support in their COVID-19 response plans, but major gaps and concerns remain.\n" +
                        "\n" +
                        "“The information we have now about the impact of COVID-19 on the world’s mental health is just the tip of the iceberg,” said Dr Tedros Adhanom Ghebreyesus, WHO Director-General. “This is a wake-up call to all countries to pay more attention to mental health and do a better job of supporting their populations’ mental health.”",
                image_url = "https://cdn1.stayprepared.sg/wp-content/uploads/sites/13/2020/07/impact-of-covid-19-1.jpg"
            ),
            Article(
                title = "Third round of the global pulse survey on continuity of essential health services during the COVID-19 pandemic",
                description = "To better understand the extent of essential health service disruptions caused by the COVID-19 pandemic",
                content = "To better understand the extent of essential health service disruptions caused by the COVID-19 pandemic, WHO has conducted three rounds of the Global pulse survey on continuity of essential health services during the COVID-19 pandemic. This report presents global findings from 129 countries, territories and areas that participated in the third round of the survey during November - December 2021. " +
                        "The findings offer critical insight from country key informants into the impact of the COVID-19 pandemic on essential health services, the challenges health systems are facing to ensure access to essential COVID-19 tools (including COVID-19 diagnostics, COVID-19 therapeutics, COVID-19 vaccines and personal protective equipment), and how countries are responding to mitigate disruptions, recover services, and strengthen health service resilience over the long-term.",
                image_url = "https://www.paho.org/sites/default/files/styles/max_1500x1500/public/2021-10/trabajador-haciendoencuesta-1500x750.jpg?itok=oBhhAc_P"
            ),
        )
    }

}
