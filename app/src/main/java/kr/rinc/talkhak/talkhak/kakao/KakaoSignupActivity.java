package kr.rinc.talkhak.talkhak.kakao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import kr.rinc.talkhak.talkhak.Activity.JoinTokenActivity;
import kr.rinc.talkhak.talkhak.Activity.LoginActivity;
import kr.rinc.talkhak.talkhak.Activity.MainActivity;
import kr.rinc.talkhak.talkhak.util.SharedUtil;

/**
 * Created by geniusk on 2017. 12. 16..
 */
public class KakaoSignupActivity extends Activity {
    /**
     * Main으로 넘길지 가입 페이지를 그릴지 판단하기 위해 me를 호출한다.
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("kakao","requset");
        requestMe();
        Log.d("kakao","requset end");
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() { //유저의 정보를 받아오는 함수
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Log.d("kakao","fail"+message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("kakao","sessionClose");
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {
                Log.d("kakao","notsign");
            } // 카카오톡 회원이 아닐 시 showSignup(); 호출해야함

            @Override
            public void onSuccess(UserProfile userProfile) {  //성공 시 userProfile 형태로 반환
                String kakaoID = String.valueOf(userProfile.getId()); // userProfile에서 ID값을 가져옴
                String kakaoNickname = userProfile.getNickname();     // Nickname 값을 가져옴
                Log.d("UserProfile : " , kakaoID);
                Log.d("UserProfile : " , kakaoNickname);
                SharedUtil.INSTANCE.setToken(getApplicationContext(), kakaoID,2);
                redirectMainActivity(); // 로그인 성공시 MainActivity로
            }
        });
    }

    private void redirectMainActivity() {
        Log.d("kakao","main");
        startActivity(new Intent(this, JoinTokenActivity.class));
        finish();
    }
    protected void redirectLoginActivity() {
        Log.d("kakao","login");
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

}
