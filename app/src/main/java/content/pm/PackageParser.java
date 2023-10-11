package content.pm;

import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Trace;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Pair;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PackageParser {

/*    public static ApkLite parseApkLite(File apkFile, int flags) {
        return parseApkLiteInner(apkFile, null, null, flags);
    }

    private static ApkLite parseApkLiteInner(File apkFile, FileDescriptor fd, String debugPathName,
                                             int flags) {
        final String apkPath = apkFile.getAbsolutePath();

        XmlResourceParser parser = null;
        try {
            final ApkAssets apkAssets;
            try {
                apkAssets = ApkAssets.loadFromPath(apkPath);
            } catch (IOException e) {
                throw new PackageParserException(INSTALL_PARSE_FAILED_NOT_APK,
                        "Failed to parse " + apkPath);
            }

            parser = apkAssets.openXml(ANDROID_MANIFEST_FILENAME);



            final AttributeSet attrs = parser;
            return parseApkLite(apkPath, parser, attrs, signingDetails);

        } catch (Exception e) {

        } finally {
            IoUtils.closeQuietly(parser);
        }
    }

    private static String parseApkLite(XmlPullParser parser, AttributeSet attrs) {
        String packageName = parsePackageSplitNames(parser, attrs);
        return packageName;
    }

    private String parseBaseApk(File apkFile, AssetManager assets, int flags) {
        final String apkPath = apkFile.getAbsolutePath();


        XmlResourceParser parser = null;
        try {
            final int cookie = assets.findCookieForPath(apkPath);
            parser = assets.openXmlResourceParser(cookie, ANDROID_MANIFEST_FILENAME);

            String pkg = parseBaseApk(parser);
            return pkg;

        } catch (Exception e) {

        } finally {
            IoUtils.closeQuietly(parser);
        }
    }

    private String parseBaseApk(XmlResourceParser parser) throws XmlPullParserException, IOException {

        String packageName = parsePackageSplitNames(parser, parser);
        return packageName;
    }

    private static String parsePackageSplitNames(XmlPullParser parser,
                                                               AttributeSet attrs) throws IOException, XmlPullParserException{
        int type;
        while ((type = parser.next()) != XmlPullParser.START_TAG
                && type != XmlPullParser.END_DOCUMENT) {
        }

        final String packageName = attrs.getAttributeValue(null, "package");
        return packageName;
    }*/
}
