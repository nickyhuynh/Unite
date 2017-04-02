package com.powergroup.unite.user_profile;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.powergroup.unite.R;
import com.powergroup.unite.app.Application;
import com.powergroup.unite.app.GenericActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by Elise on 4/1/2017.
 */

public class EditProfileActivity extends GenericActivity {

    private TextView userName;
    private TextView userAge;
    private TextView userGender;
    private TextView userCountry;
    private TextView userLanguages;
    private TextView userBio;
    protected ImageView previewImageView;
    public ImageView profilePic;
    public Button editButton;
    private Spinner genderSpinner;
//    private Spinner countrySpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        addListenerOnButton();

        // initialize variables
        genderSpinner = (Spinner) findViewById(R.id.spinner_gender);
//        countrySpinner = (Spinner) findViewById(R.id.spinner_country);
        profilePic = (ImageView) findViewById(R.id.profile_image);
        userName = (TextView) findViewById(R.id.user_name);
        userAge = (TextView) findViewById(R.id.user_age);
        userGender = (TextView) findViewById(R.id.user_gender);
        userCountry = (TextView) findViewById(R.id.user_country);
        userLanguages = (TextView) findViewById(R.id.user_languages);
        userBio = (TextView) findViewById(R.id.user_bio);
        editButton = (Button) findViewById(R.id.edit_button);

        // setting profile pic to sample
        // Bitmap sampleProfilePic = BitmapFactory.decodeResource(getResources(), R.drawable.sample_profile_pic);

//        profilePic.setImageDrawable(getResources().getDrawable(R.drawable.sample_profile_pic));
        Picasso.with(Application.getInstance()).load(R.drawable.sample_profile_pic).into(profilePic);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.countries_list);
        textView.setAdapter(adapter);


        // initiate a MultiAutoCompleteTextView
        MultiAutoCompleteTextView simpleMultiAutoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.languages_list);
// set adapter to fill the data in suggestion list
        ArrayAdapter<String> versionNames = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, LANGUAGES);
        simpleMultiAutoCompleteTextView.setAdapter(versionNames);

// set threshold value 1 that help us to start the searching from first character
        simpleMultiAutoCompleteTextView.setThreshold(1);
// set tokenizer that distinguish the various substrings by comma

        simpleMultiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }


        public void addListenerOnSpinnerItemSelection() {
            genderSpinner = (Spinner) findViewById(R.id.spinner_gender);
            genderSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

//            countrySpinner = (Spinner) findViewById(R.id.spinner_country);
//            countrySpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        genderSpinner = (Spinner) findViewById(R.id.spinner_gender);
//        countrySpinner = (Spinner) findViewById(R.id.spinner_country);

    }

    private static final String[] COUNTRIES = new String[] {
            "Afghanistan", "Aland Islands", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
            "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda",
            "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "British Virgin Islands",
            "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic",
            "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Cook Islands", "Costa Rica", "Cote d'Ivoire",
            "Croatia", "Cuba", "Cyprus", "Czech Republic", "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor",
            "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji", "Finland",
            "Former Yugoslav Republic of Macedonia", "France", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Georgia", "Germany",
            "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti",
            "Heard Island and McDonald Islands", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy",
            "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
            "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique",
            "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia",
            "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea",
            "Northern Marianas", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Islands", "Poland", "Portugal",
            "Puerto Rico", "Qatar", "Reunion", "Romania", "Russia", "Rwanda", "Sqo Tome and Principe", "Saint Helena", "Saint Kitts and Nevis", "Saint Lucia", "Saint Pierre and Miquelon",
            "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands",
            "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "South Korea", "Spain", "Sri Lanka", "Sudan", "Suriname", "Svalbard and Jan Mayen",
            "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "The Bahamas", "The Gambia", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago",
            "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Virgin Islands", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States",
            "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Wallis and Futuna", "Western Sahara", "Yemen", "Yugoslavia",
            "Zambia", "Zimbabwe"
    };

    private static final String[] LANGUAGES = new String[]{

    "Afrikanns", "Albanian", "Arabic", "Armenian", "Basque", "Bengali", "Bulgarian", "Catalan", "Cambodian", "Chinese (Mandarin)", "Croation", "Czech", "Danish",
    "Dutch", "English", "Estonian", "Fiji", "Finnish", "French", "Georgian", "German", "Greek", "Gujarati", "Hebrew", "Hindi", "Hungarian", "Icelandic", "Indonesian",
            "Irish", "Italian", "Japanese", "Javanese", "Korean", "Latin", "Latvian", "Lithuanian", "Macedonian", "Malay", "Malayalam", "Maltese", "Maori", "Marathi",
            "Mongolian", "Nepali", "Norwegian", "Persian", "Polish", "Portuguese", "Punjabi", "Quechua", "Romanian", "Russian", "Samoan", "Serbian", "Slovak", "Slovenian",
            "Spanish", "Swahili", "Swedish ", "Tamil", "Tatar", "Telugu", "Thai", "Tibetan", "Tonga", "Turkish", "Ukranian", "Urdu", "Uzbek", "Vietnamese", "Welsh", "Xhosa"

    };
}
