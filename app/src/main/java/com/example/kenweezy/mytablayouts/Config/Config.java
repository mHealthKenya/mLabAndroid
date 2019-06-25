package com.example.kenweezy.mytablayouts.Config;

public class Config {

    public static String mainShortcode="40147";
    public static String sendSmsShortcode="40147";
    public static String registerShortcode="40147";

    public static final String JSON_ARRAYRESULTS = "results";
    public static final String KEY_MESSAGECODE="message";

    public static final String[] SPINNERLISTLABS = {"Kemri Nairobi",
            "Kisumu lab",
            "Alupe",
            "Walter Reed",
            "Ampath",
            "Coast lab",
            "KNH"};

    public static final String[] SPINNERLISTSEX = {"Male", "Female"};
    public static final String[] SPINNERLISTDELIVERYPOINT = {"OPD", "MCH","IPD","CCC","Community"};
    public static final String[] SPINNERLISTTESTKITS = {"Screening kit-determine", "Confirmatory-first response"};
    public static final String[] SPINNERLISTFINALRESULTS = {"Negative", "Positive"};
    public static final String[] SPINNERLISTENTRYPOINT = {"IPD", "OPD","MATERNITY","CCC","MCH/PMTCT","Other"};
    public static final String[] SPINNERLISTPROPHYLAXISCODE = {"AZT for 6 weeks + NVP for 12 weeks", "AZT for 6 weeks + NVP for >12 weeks","None","Other"};
    public static final String[] SPINNERLISTINFANTFEEDING = {"EBF (Exclusive Breast Feeding)", "ERF (Exclusive Replacement Feeding)","MF (Mixed feeding)","BF (Breast Feeding)","NBF (Not Breast Feeding)"};
    public static final String[] SPINNERLISTPCR = {"1= Initial PCR","2= 2nd PCR","3= 3rd PCR","4= Comfirmatory PCR and Baseline VL","5= Discrepant PCR (tie breaker)","6= Sample redraw"};




    public static final String[] SPINNERLISTALIVEDEAD = {"Alive", "Dead"};


    public static final String[] SPINNERLISTREGIMEN = {"PM1X=Any other Regimen",
            "PM3= AZT+3TC+NVP",
            "PM4= AZT+ 3TC+ EFV",
            "PM5= AZT+3TC+ LPV/r",
            "PM6= TDC+3TC+NVP",
            "PM7= TDF+3TC+LPV/r",
            "PM9= TDF+3TC+EFV",
            "PM10= AZT+3TC+ATV/r",
            "PM11= TDF+3TC+ATV/r",
            "PM12=TDF+3TC+DTG",
            "PM13=None"};
    public static final String[] SPINNERLISTSAMPLETYPE = {"Frozen plasma","Venous blood(EDTA)","DBS capillary(infants only)","DBS venous","PPT"};


    public static final String EIDVL_DATA_URL = "https://mlab.mhealthkenya.co.ke/api/remote/login/results";
    public static final String HTS_DATA_URL = "https://mlab.mhealthkenya.co.ke/api/remote/login/hts";
    public static final String RESULTS_DATA_URL = "https://mlab.mhealthkenya.co.ke/api/get/results";
    public static final String HISTORICALRESULTS_DATA_URL = "https://mlab.mhealthkenya.co.ke/api/historical/results";

    public static final String GETHTSRESULTS_DATA_URL = "https://mlab.mhealthkenya.co.ke/api/hts_results";
    public static final String GETTBRESULTS_DATA_URL = "https://mlab.mhealthkenya.co.ke/api/tb_results";


}
