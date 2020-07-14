package com.app.sitaramswami

/**
 * Created by kartik on 05-May-18.
 */
data class LoginData(
        var username: String,
        var password: String
)

data class SignUpData(
        var name: String,
        var email: String,
        var phone: String,
        var password: String
)
data class Email(

        var email: String
)

data class ShopListResponse(
        var status:Int,
        var message:String,
        var id:Int,
        var name:String,
        var address_first:String,
        var address_second:String,
        var city:String,
        var state:String,
        var phone:String,
        var make:String,
        var full_title:String,
        var email:String,
        var whatsapp_no:String,
        var price:String,
        var video_url:String,
        var data:ShopListResponse,
        var lists:ArrayList<ShopListResponse>,
        var images:ArrayList<String>,
        var image:String,
        var fav:String,
        var year:String,
        var km_drive:String,
        var fuel:String,
        var updated_on:String,
        var car_model:String

)
data class ProductListResponse(
        var status:Int,
        var message:String,
        var id:Int,
        var name:String,
        var address_first:String,
        var address_second:String,
        var city:String,
        var state:String,
        var phone:String,
        var make:String,
        var full_title:String,
        var email:String,
        var whatsapp_no:String,
        var price:String,
        var video_url:String,
        var data:ShopListResponse,
        var lists:ArrayList<ShopListResponse>,
        var images:ArrayList<String>,
        var year:String,
        var km_drive:String,
        var fuel:String,
        var updated_on:String,

        var fav:String,
        var car_make:String,

        var car_model:String,
        var registration_place:String,
        var owner:String,
        var type:String,
        var transmission:String,
        var color:String,
        var type_of_car:String,
        var insurance_type:String,
        var power_steering:String,
        var cruise_control:String,
        var power_windows:String,
        var navigation_system:String,
        var back_camera:String,
        var adjustable_steering:String,
        var automatic_boot:String,
        var air_conditioning:String,
        var sunroof:String,
        var discription:String,
        var fog_lamps:String,
        var alloy_wheels:String,
        var rear_window_wiper:String,
        var adjustable_external_mirror:String


)
data class ProductId(
        var id:String,
        var user_id:String


)
data class Product(
        var id:String

)
data class UserId(
        var user_id:String


)
data class FavCar(
        var user_id:String,
        var id:String


)
data class BannerListResponse(
            var status:Int,
            var message:String,
            var about:String,
            var data:BannerListResponse,
            var images:ArrayList<String>,
            var exp:ArrayList<String>
)
data class AboutUs(
        var status:Int,
        var message:String,
        var data:ArrayList<String>
)
data class ChangePasswordData(
        var  email:String,
        var  password: String,
        var  verifyPassword: String
)
data class SocialData(
        var first_name: String?,
        var email: String?,
        var phone: String?,
        var profile_image: String?,
        var login_type: String?

)

data class LoginResponse(
        var id: Int,
        var email: String,
        var status: String,
        var firstname: String,
        var gender: String,
        var username: String,
        var password: String

)
data class AdsIdResponse(
    var add_id:String,
    var mobile_add_id:String

)
data class ProfileResponse(
        var image:String,
        var name:String,
        var city:String,
        var phone:String,
        var data:ProfileResponse,
        var email:String,
        var website:String,
        var w_number:String,
        var text:String
)
data class EnquiryData(
        var customer_name: String,
        var email: String,
        var phone: String,
        var address: String,
        var city:String,
        var state: String,
        var vehicle_name: String,
        var message: String,
        var user_id:String
)
data class FeedBackData(
        var name: String,
        var email: String,
        var phone: String,
        var message: String,
        var user_id:String
)