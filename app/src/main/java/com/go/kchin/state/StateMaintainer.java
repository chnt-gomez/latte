package com.go.kchin.state;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by MAV1GA on 29/12/2016.
 */

public class StateMaintainer {

    protected final String TAG = getClass().getSimpleName();

    private final String mStateMaintainerTag;
    private final WeakReference<FragmentManager> mFragmentManager;
    private StateMngFragment mStateMaintainerFrag;

    /**
     * Constructor
     * @param fragmentManager FragmentManager reference
     * @param mStateMaintainerTag the TAG used to insert the state maintainer fragment
     */
    public StateMaintainer (FragmentManager fragmentManager, String mStateMaintainerTag){
        this.mFragmentManager = new WeakReference<>(fragmentManager);
        this.mStateMaintainerTag = mStateMaintainerTag;
    }


    /**
     * Create the state maintainer fragment
     * @return  true: the frag was created for the first time
     *          false: recovering the object
     */
    public boolean firstTimeIn(){
        try{
            //Recovering the reference
            mStateMaintainerFrag = (StateMngFragment) mFragmentManager.get().
                    findFragmentByTag(mStateMaintainerTag);

            //Create a new Retained Fragment
            if (mStateMaintainerFrag == null){
                Log.d(TAG, "Create a new Retained Fragment "+mStateMaintainerTag);
                mStateMaintainerFrag = new StateMngFragment();
                mFragmentManager.get().beginTransaction()
                        .add(mStateMaintainerFrag, mStateMaintainerTag).commit();
                return true;
            }else{
                Log.d(TAG, "Returns an existing retained fragment existence " +
                mStateMaintainerTag);
                return false;
            }
        }catch(NullPointerException e){
            Log.w(TAG, "Error firstTimeIn()");
            return false;
        }
    }

    /**
     * Insert Object to be preserved during configuration change
     * @param key Object TAG reference
     * @param obj Object to maintain
     */
    public void put(String key, Object obj){
        mStateMaintainerFrag.put(key, obj);
    }

    /**
     * Insert Object to be preserved during configuration change
     * Uses the Object's class name as TAG reference
     * Should only be used one time by type class
     * @param obj Object to maintain
     */
    public void put(Object obj){
        put(obj.getClass().getName(), obj);
    }


    /**
     * Recovers saved object
     * @param key TAG reference
     * @param <T> Class type
     * @return Objects
     */
    @SuppressWarnings("unchecked")
    public <T> T get (String key){
        return mStateMaintainerFrag.get(key);
    }


    /**
     * Verify the object existence
     * @param key Object TAG
     */
    public boolean hasKey(String key){
        return mStateMaintainerFrag.get(key) != null;
    }

    public static class StateMngFragment extends Fragment{
        private HashMap<String, Object> mData= new HashMap<>();

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //Grants that the frag will be preserved
            setRetainInstance(true);
        }

        /**
         * Insert object using classname as TAG
         * @param key reference TAG
         * @param object Object to save
         */
        public void put(String key, Object object){
            mData.put(key, object);
        }

        /**
         * Recover obj
         * @param key reference TAG
         * @param <T> Class
         * @return object saved
         */
        @SuppressWarnings("unchecked")
        public <T> T get(String key){
            return (T)mData.get(key);
        }
    }

}
