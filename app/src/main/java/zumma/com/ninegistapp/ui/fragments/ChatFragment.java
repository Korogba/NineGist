package zumma.com.ninegistapp.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.parse.ParseUser;

import zumma.com.ninegistapp.ParseConstants;
import zumma.com.ninegistapp.R;
import zumma.com.ninegistapp.custom.CustomFragment;
import zumma.com.ninegistapp.model.Conversation;
import zumma.com.ninegistapp.ui.Components.LayoutListView;
import zumma.com.ninegistapp.ui.adapters.ChatAdapter;
import zumma.com.ninegistapp.ui.adapters.ChatArrayList;
import zumma.com.ninegistapp.ui.helpers.GDate;
import zumma.com.ninegistapp.ui.listeners.UserChatListeners;

public class ChatFragment extends CustomFragment {


    private ChatAdapter adp;
    private ChatArrayList convList;
    private EditText txt;

    private String user_id;

    private UserChatListeners userChatListeners;

    private Firebase listnerBase;

    private static final String TAG = ChatFragment.class.getSimpleName();

    private String friend_id;

    public ChatAdapter getAdp() {
        return adp;
    }

    public ChatArrayList getConvList() {
        return convList;
    }

    private void loadConversationList() {
        Bundle bundle = this.getArguments();
        friend_id = bundle.getString(ParseConstants.KEY_USER_ID);

        user_id = ParseUser.getCurrentUser().getObjectId();

        this.convList = new ChatArrayList(getActivity(), friend_id);

        this.adp = new ChatAdapter(getActivity(),convList);

        listnerBase = new Firebase(ParseConstants.FIREBASE_URL).child("9Gist").child(friend_id).child("chats").child(user_id);
        userChatListeners = new UserChatListeners(adp, convList);
        listnerBase.addChildEventListener(userChatListeners);

        Log.d(TAG,"  path : "+ listnerBase.getPath().toString());
    }

    private void sendMessage() {

        if (this.txt.length() == 0)
            return;


        final Firebase uFirebaseRef = new Firebase(ParseConstants.FIREBASE_URL).child("9Gist").child(user_id).child("chats").child(friend_id);
        final Firebase cFirebaseRef = new Firebase(ParseConstants.FIREBASE_URL).child("9Gist").child(friend_id).child("chats").child(user_id);

        GDate date = new GDate();

        String str = this.txt.getText().toString();
        String time = date.getCurrent_time();

        final Conversation conversation = new Conversation(user_id, friend_id,time, true, str);

        uFirebaseRef.push().setValue(conversation, new Firebase.CompletionListener() {

            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
                    Log.d(TAG, " CHAT SAVED  " + firebase.getKey());
                    conversation.setUniqkey(firebase.getKey());
                    conversation.setReport(1);
                    cFirebaseRef.push().setValue(conversation);
                    uFirebaseRef.child(firebase.getKey()).setValue(conversation, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            conversation.setReport(1);
                            Log.d(TAG, conversation.toString());
                            convList.upDateDeliveredConversation(conversation);
                            adp.notifyDataSetChanged();

                        }
                    });
                }
            }
        });

        Log.d(TAG, conversation.toString());
        convList.addNewChat(conversation);
        adp.notifyDataSetChanged();
        this.txt.setText(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        listnerBase.removeEventListener(userChatListeners);
    }

    public void onClick(View paramView) {
        super.onClick(paramView);

        if (paramView.getId() == R.id.chat_send_button)
            sendMessage();
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View localView = paramLayoutInflater.inflate(R.layout.chat_layout, null);
        loadConversationList();
        LayoutListView localListView = (LayoutListView) localView.findViewById(R.id.chat_list_view);

        localListView.setAdapter(this.adp);
        localListView.setTranscriptMode(2);
        localListView.setStackFromBottom(true);
        this.txt = ((EditText) localView.findViewById(R.id.chat_text_edit));
        this.txt.setInputType(131073);
        setTouchNClick(localView.findViewById(R.id.chat_send_button));

        return localView;
    }

}
