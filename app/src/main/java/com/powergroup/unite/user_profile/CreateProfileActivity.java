package com.powergroup.unite.user_profile;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.powergroup.unite.R;
import com.powergroup.unite.app.Application;
import com.powergroup.unite.app.CircularImageView;
import com.powergroup.unite.app.GenericActivity;
import com.powergroup.unite.app.Profile;
import com.squareup.picasso.Picasso;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Elise on 4/2/2017.
 *
 * User's profile, which will feature name, age, bio, the flag icon and nation that person is representing, what sports, etc.
 */

public class CreateProfileActivity extends GenericActivity {

    private TextView userName;
    private TextView userAge;
    private TextView userGender;
    private AutoCompleteTextView userCountry;
    private TextView userLanguages;
    private TextView userBio;
    public ImageView profilePic;
    private Spinner genderSpinner;
    private MultiAutoCompleteTextView multi;
    private MultiAutoCompleteTextView simpleMultiAutoCompleteTextView;

    private String profileImg;
    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        addListenerOnButton();

        // initialize variables
        genderSpinner = (Spinner) findViewById(R.id.spinner_gender);
//        countrySpinner = (Spinner) findViewById(R.id.spinner_country);
        profilePic = (ImageView) findViewById(R.id.profile_image);
        userName = (TextView) findViewById(R.id.user_name);
        userAge = (TextView) findViewById(R.id.user_age);
        userGender = (TextView) findViewById(R.id.user_gender);
        userCountry = (AutoCompleteTextView) findViewById(R.id.countries_list);
        userLanguages = (TextView) findViewById(R.id.user_languages);
        userBio = (TextView) findViewById(R.id.user_bio);

        // setting profile pic to sample
        // Bitmap sampleProfilePic = BitmapFactory.decodeResource(getResources(), R.drawable.sample_profile_pic);

//        profilePic.setImageDrawable(getResources().getDrawable(R.drawable.sample_profile_pic));
        Bundle params = new Bundle();
        params.putString("fields", "id,email,gender,cover,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            try {
                                JSONObject data = response.getJSONObject();
                                if (data.has("picture")) {
                                    String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                    Picasso.with(Application.getInstance()).load(profilePicUrl).into(profilePic);

                                    profileImg = profilePicUrl;
                                    com.facebook.Profile profile = com.facebook.Profile.getCurrentProfile();
                                    userName.setText(profile.getFirstName());
                                    id = profile.getId();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).executeAsync();

        Picasso.with(Application.getInstance()).load(R.drawable.sample_profile_pic).into(profilePic);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.countries_list);
        textView.setAdapter(adapter);


        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, getResources().getStringArray(R.array.gender_arrays));
        genderSpinner.setAdapter(spinAdapter);


        // initiate a MultiAutoCompleteTextView
        simpleMultiAutoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.languages_list);
// set adapter to fill the data in suggestion list
        ArrayAdapter<String> versionNames = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, LANGUAGES);
        simpleMultiAutoCompleteTextView.setAdapter(versionNames);

// set threshold value 1 that help us to start the searching from first character
        simpleMultiAutoCompleteTextView.setThreshold(1);
// set tokenizer that distinguish the various substrings by comma
        simpleMultiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        //this is for the ethnicities
        multi = (MultiAutoCompleteTextView) findViewById(R.id.ethnicities_list);
// set adapter to fill the data in suggestion list
        ArrayAdapter<String> names = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ETHNICITIES);
        multi.setAdapter(names);

// set threshold value 1 that help us to start the searching from first character
        multi.setThreshold(1);
// set tokenizer that distinguish the various substrings by comma
        multi.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }


    public void addListenerOnSpinnerItemSelection() {
        genderSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        genderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        LinearLayout confirmButton = (LinearLayout) findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString();
                String age = userAge.getText().toString();
                String bio = userBio.getText().toString();
                String nation = userCountry.getText().toString();
                String[] ethnicities = multi.getText().toString().replace(",", " ").replaceAll(" +", " ").split(" ");
                String[] languages = simpleMultiAutoCompleteTextView.getText().toString().replace(",", " ").replaceAll(" +", " ").split(" ");
//                com.facebook.Profile profile = com.facebook.Profile.getCurrentProfile();
//                String id = profile.getId();

                Log.d("zxcvzxcv", "id: " + id);

                if(name.length() > 0 &&
                        age.length() > 0 &&
                        bio.length() > 0 &&
                        nation.length() > 0 &&
                        ethnicities.length > 0 &&
                        languages.length > 0) {
                    Profile.INSTANCE.info.setInfo(name, age, bio, nation, genderSpinner.getSelectedItem().toString(), ethnicities, languages, profileImg, id, new HashMap<String, Profile.ProfileInfo>(), new ArrayList<String>());
                    SharedPreferences preferences = getSharedPreferences("UNIFY", Context.MODE_PRIVATE);
                    preferences.edit().putString("profile_info", new Gson().toJson(Profile.INSTANCE.info)).apply();
                    Map<String, Profile.ProfileInfo> map = new HashMap<>();

                    try {
                        map.put(id, Profile.INSTANCE.info);
                        FirebaseDatabase.getInstance().getReference().child("/profiles").setValue(map);
                    } catch (Exception e) {
                        Log.d("asdfasdf", e.getMessage());
                    }
                    navigateToMain();
                } else {
                    Toast.makeText(Application.getInstance(), "There are one or more fields that need filling in!", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

            "Afrikanns", "Albanian", "Arabic", "Armenian", "Basque", "Bengali", "Bulgarian", "Catalan", "Cambodian", "Mandarin", "Croation", "Czech", "Danish",
            "Dutch", "English", "Estonian", "Fiji", "Finnish", "French", "Georgian", "German", "Greek", "Gujarati", "Hebrew", "Hindi", "Hungarian", "Icelandic", "Indonesian",
            "Irish", "Italian", "Japanese", "Javanese", "Korean", "Latin", "Latvian", "Lithuanian", "Macedonian", "Malay", "Malayalam", "Maltese", "Maori", "Marathi",
            "Mongolian", "Nepali", "Norwegian", "Persian", "Polish", "Portuguese", "Punjabi", "Quechua", "Romanian", "Russian", "Samoan", "Serbian", "Slovak", "Slovenian",
            "Spanish", "Swahili", "Swedish ", "Tamil", "Tatar", "Telugu", "Thai", "Tibetan", "Tonga", "Turkish", "Ukranian", "Urdu", "Uzbek", "Vietnamese", "Welsh", "Xhosa"

    };

    private static final String[] ETHNICITIES = new String[] {
            "Afghan", "Albanian", "Algerian", "American", "Andorran", "Angolan", "Antiguans", "Argentinean",
            "Armenian" , "Australian", "Austrian" , "Azerbaijani" , "Bahamian" , "Bahraini" , "Bangladeshi" ,
            "Barbadian" , "Barbudans" , "Batswana" , "Belarusian" , "Belgian" , "Belizean" , "Beninese" ,
            "Bhutanese" , "Bolivian" , "Bosnian" , "Brazilian" , "British" , "Bruneian" , "Bulgarian" ,
            "Burkinabe" , "Burmese" , "Burundian" , "Cambodian" , "Cameroonian" , "Canadian" , "Cape Verdean" ,
            "Central African" , "Chadian" , "Chilean" , "Chinese" , "Colombian" , "Comoran" , "Congolese" ,
            "Congolese" , "Costa Rican" , "Croatian" , "Cuban" , "Cypriot" , "Czech" , "Danish" , "Djibouti" ,
            "Dominican" , "Dominican" , "Dutch" , "Dutchman" , "Dutchwoman" , "East Timorese" , "Ecuadorean" ,
            "Egyptian" , "Emirian" , "Equatorial Guinean" , "Eritrean" , "Estonian" , "Ethiopian" , "Fijian" ,
            "Filipino" , "Finnish" , "French" , "Gabonese" , "Gambian" , "Georgian" , "German" , "Ghanaian" ,
            "Greek" , "Grenadian" , "Guatemalan" , "Guinea-Bissauan" , "Guinean" , "Guyanese" , "Haitian" ,
            "Herzegovinian" , "Honduran" , "Hungarian" , "I-Kiribati" , "Icelander" , "Indian" , "Indonesian" ,
            "Iranian" , "Iraqi" , "Irish" , "Irish" , "Israeli" , "Italian" , "Ivorian" , "Jamaican" ,
            "Japanese" , "Jordanian" , "Kazakhstani" , "Kenyan" , "Kittian and Nevisian" , "Kuwaiti" ,
            "Kyrgyz" , "Laotian" , "Latvian" , "Lebanese" , "Liberian" , "Libyan" , "Liechtensteiner" ,
            "Lithuanian" , "Luxembourger" , "Macedonian" , "Malagasy" , "Malawian" , "Malaysian" , "Maldivan" ,
            "Malian" , "Maltese" , "Marshallese" , "Mauritanian" , "Mauritian" , "Mexican" , "Micronesian" ,
            "Moldovan" , "Monacan" , "Mongolian" , "Moroccan" , "Mosotho" , "Motswana" , "Mozambican" ,
            "Namibian" , "Nauruan" , "Nepalese" , "Netherlander" , "New Zealander" , "Ni-Vanuatu" , "Nicaraguan" ,
            "Nigerian" , "Nigerien" , "North Korean" , "Northern Irish" , "Norwegian" , "Omani" , "Pakistani" ,
            "Palauan" , "Panamanian" , "Papua New Guinean" , "Paraguayan" , "Peruvian" , "Polish" , "Portuguese" ,
            "Qatari" , "Romanian" , "Russian" , "Rwandan" , "Saint Lucian" , "Salvadoran" , "Samoan" , "San Marinese" ,
            "Sao Tomean" , "Saudi" , "Scottish" , "Senegalese" , "Serbian" , "Seychellois" , "Sierra Leonean" , "Singaporean" ,
            "Slovakian" , "Slovenian" , "Solomon Islander" , "Somali" , "South African" , "South Korean" , "Spanish" ,
            "Sri Lankan" , "Sudanese" , "Surinamer" , "Swazi" , "Swedish" , "Swiss" , "Syrian" , "Taiwanese" , "Tajik" ,
            "Tanzanian" , "Thai" , "Togolese" , "Tongan" , "Trinidadian or Tobagonian" , "Tunisian" , "Turkish" ,
            "Tuvaluan" , "Ugandan" , "Ukrainian" , "Uruguayan" , "Uzbekistani" , "Venezuelan" , "Vietnamese" , "Welsh" ,
            "Welsh" , "Yemenite" , "Zambian" , "Zimbabwean"
    };
}