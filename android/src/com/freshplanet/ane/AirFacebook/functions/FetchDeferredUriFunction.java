package com.freshplanet.ane.AirFacebook.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREObject;
import com.facebook.applinks.AppLinkData;
import com.freshplanet.ane.AirFacebook.AirFacebookExtension;

public class FetchDeferredUriFunction extends BaseFunction
{
	public FREObject call(FREContext context, FREObject[] args)
	{
		super.call(context, args);

		final String callback = getStringFromFREObject(args[0]);

		AppLinkData.fetchDeferredAppLinkData(context.getActivity().getApplication(), new AppLinkData.CompletionHandler() {
					@Override
					public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
						AirFacebookExtension.log("Got app link data: " + appLinkData);
						if (appLinkData != null && appLinkData.getTargetUri() != null) {
							String uri = appLinkData.getTargetUri().toString();
							if (uri.length() > 0) {
								AirFacebookExtension.log("App link data uri: " + uri);
								AirFacebookExtension.context.dispatchStatusEventAsync("DEFERRED_" + callback, uri);
							}
						}
					}
				}
		);

		return null;
	}
}