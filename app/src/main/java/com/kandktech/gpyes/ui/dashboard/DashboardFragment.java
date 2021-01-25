package com.kandktech.gpyes.ui.dashboard;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kandktech.gpyes.R;

public class DashboardFragment extends Fragment {

   View view;
String privacy = "<strong>Privacy Policy</strong></p>\n" +
        "\n" +
        "<p>K. And K. Tech Pvt. Ltd. built the GpYes app as a Free app. This SERVICE is provided by K. And K. Tech Pvt. Ltd. at no cost and is intended for use as is.</p>\n" +
        "\n" +
        "<p>This page is used to inform visitors regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our Service.</p>\n" +
        "\n" +
        "<p>If you choose to use our Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that we collect is used for providing and improving the Service. We will not use or share your information with anyone except as described in this Privacy Policy.</p>\n" +
        "\n" +
        "<p>The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which is accessible at GpYes unless otherwise defined in this Privacy Policy.</p>\n" +
        "\n" +
        "<p><strong>Information Collection and Use</strong></p>\n" +
        "\n" +
        "<p>For a better experience, while using our Service, we may require you to provide us with certain personally identifiable information, including but not limited to Location, Name, Email. The information that we request will be retained by us and used as described in this privacy policy.&nbsp;</p>\n" +
        "\n" +
        "<p>Once you click on the start button inside the application, the location will continuously send to the server every 5minutes even after you close the app or minimize the application. Through this app, the staff of a company could make attendance and the location that sent to the server will be used by the company to track the office staff mostly targeted to marketing staff.&nbsp;</p>\n" +
        "\n" +
        "<p>After clicking on the stop button your last location will be used by a company as a checkout from office time. And the application will stop sending location to server.</p>\n" +
        "\n" +
        "<div>\n" +
        "<p>The app does use third-party services that may collect information used to identify you.</p>\n" +
        "\n" +
        "<p>Link to the privacy policy of third party service providers used by the app</p>\n" +
        "\n" +
        "<ul>\n" +
        "\t<li><a href=\"https://www.google.com/policies/privacy/\" rel=\"noopener noreferrer\" target=\"_blank\">Google Play Services</a></li>\n" +
        "</ul>\n" +
        "</div>\n" +
        "\n" +
        "<p><strong>Log Data</strong></p>\n" +
        "\n" +
        "<p>We want to inform you that whenever you use our Service, in case of an error in the app we collect data and information (through third-party products) on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (&ldquo;IP&rdquo;) address, device name, operating system version, the configuration of the app when utilizing our Service, the time and date of your use of the Service, and other statistics.</p>\n" +
        "\n" +
        "<p><strong>Cookies</strong></p>\n" +
        "\n" +
        "<p>Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device&#39;s internal memory.</p>\n" +
        "\n" +
        "<p>This Service does not use these &ldquo;cookies&rdquo; explicitly. However, the app may use third party code and libraries that use &ldquo;cookies&rdquo; to collect information and improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.</p>\n" +
        "\n" +
        "<p><strong>Service Providers</strong></p>\n" +
        "\n" +
        "<p>We may employ third-party companies and individuals due to the following reasons:</p>\n" +
        "\n" +
        "<ul>\n" +
        "\t<li>To facilitate our Service;</li>\n" +
        "\t<li>To provide the Service on our behalf;</li>\n" +
        "\t<li>To perform Service-related services; or</li>\n" +
        "\t<li>To assist us in analyzing how our Service is used.</li>\n" +
        "</ul>\n" +
        "\n" +
        "<p>We want to inform users of this Service that these third parties have access to their Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.</p>\n" +
        "\n" +
        "<p><strong>Security</strong></p>\n" +
        "\n" +
        "<p>We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.</p>\n" +
        "\n" +
        "<p><strong>Links to Other Sites</strong></p>\n" +
        "\n" +
        "<p>This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by us. Therefore, we strongly advise you to review the Privacy Policy of these websites. We have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.</p>\n" +
        "\n" +
        "<p><strong>Children&rsquo;s Privacy</strong></p>\n" +
        "\n" +
        "<p>These Services do not address anyone under the age of 13. We do not knowingly collect personally identifiable information from children under 13. In the case we discover that a child under 13 has provided us with personal information, we immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact us so that we will be able to do necessary actions.</p>\n" +
        "\n" +
        "<p><strong>Changes to This Privacy Policy</strong></p>\n" +
        "\n" +
        "<p>We may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. We will notify you of any changes by posting the new Privacy Policy on this page.</p>\n" +
        "\n" +
        "<p>This policy is effective as of 2021-01-16</p>\n" +
        "\n" +
        "<p><strong>Contact Us</strong></p>\n" +
        "\n" +
        "<p>If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us at info@kandktechnepal.com.</p>\n" +
        "\n" +
        "<p>Contact Number : +977-9863859745</p>";

    public DashboardFragment() {

        //Required empty constructor
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container,false);
        TextView txtPri = view.findViewById(R.id.txtPri);
        txtPri.setText(Html.fromHtml(privacy).toString());
        return view;
    }
}