package io.github.pabulaner.ads_example;

import com.gluonhq.attach.ads.AdListener;
import com.gluonhq.attach.ads.AdRequest;
import com.gluonhq.attach.ads.AdsService;
import com.gluonhq.attach.ads.BannerAd;
import com.gluonhq.attach.ads.FullScreenContentCallback;
import com.gluonhq.attach.ads.InterstitialAd;
import com.gluonhq.attach.ads.InterstitialAdLoadCallback;
import com.gluonhq.attach.ads.RewardedAd;
import com.gluonhq.attach.ads.RewardedAdLoadCallback;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.logging.Logger;

public class AdView extends View {
    
    private static final Logger LOGGER = Logger.getLogger(AdView.class.getName());
    
    private static final double BUTTON_WIDTH = 300.0f;

    private AdsService adsService;

    private BannerAd bannerAd;

    private InterstitialAd interstitialAd;

    private RewardedAd rewardedAd;

    public AdView() {
        setCenter(createRoot(
                createAdsServiceNode(),
                createBannerAdNode(),
                createInterstitialAdNode(),
                createRewardedAdNode()));
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setTitleText("Ads Example");
    }

    private Node createAdsServiceNode() {
        return createVBox(
                createLabel("Ads Service"),
                createButton("create", () -> adsService = AdsService.create().orElseThrow()),
                createButton("initialize", () -> adsService.initialize(() -> LOGGER.info("Initialized!"))));
    }

    private Node createBannerAdNode() {
        int[] layoutIndex = {0};
        int[] adSizeIndex = {0};

        return createVBox(
                createLabel("Banner Ad"),
                createButton("new", () -> bannerAd = adsService.newBannerAd()),
                createButton("load", () -> bannerAd.load(new AdRequest.Builder().build())),
                createButton("setAdUnitId", () -> bannerAd.setAdUnitId(BannerAd.TEST_AD_UNIT_ID)),
                createButton("setAdListener", () -> bannerAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClicked() {
                        LOGGER.info("onAdClicked");
                    }

                    @Override
                    public void onAdClosed() {
                        LOGGER.info("onAdClosed");
                    }

                    @Override
                    public void onAdFailedToLoad() {
                        LOGGER.info("onAdFailedToLoad");
                    }

                    @Override
                    public void onAdImpression() {
                        LOGGER.info("onAdImpression");
                    }

                    @Override
                    public void onAdLoaded() {
                        LOGGER.info("onAdLoaded");
                    }

                    @Override
                    public void onAdOpened() {
                        LOGGER.info("onAdOpened");
                    }

                    @Override
                    public void onAdSwipeGestureClicked() {
                        LOGGER.info("onAdSwipeGestureClicked");
                    }
                })),
                createButton("setAdLayout", () -> {
                    BannerAd.Layout[] values = BannerAd.Layout.values();
                    BannerAd.Layout value = values[layoutIndex[0]++];

                    bannerAd.setAdLayout(value);
                    LOGGER.info("setAdLayout(" + value + ")");

                    layoutIndex[0] %= values.length;
                }),
                createButton("setAdSize", () -> {
                    BannerAd.Size[] values = BannerAd.Size.values();
                    BannerAd.Size value = values[adSizeIndex[0]++];

                    bannerAd.setAdSize(value);
                    LOGGER.info("setAdSize(" + value + ")");

                    adSizeIndex[0] %= values.length;
                }),
                createButton("show", () -> bannerAd.show()),
                createButton("hide", () -> bannerAd.hide()),
                createButton("dispose", () -> bannerAd.dispose()));
    }

    private Node createInterstitialAdNode() {
        return createVBox(
                createLabel("Interstitial Ad"),
                createButton("load", () -> adsService.loadInterstitialAd(InterstitialAd.TEST_AD_UNIT_ID, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad() {
                        LOGGER.info("onAdFailedToLoad");
                    }

                    @Override
                    public void onAdLoaded(InterstitialAd ad) {
                        interstitialAd = ad;
                        LOGGER.info("onAdLoaded");
                    }
                })),
                createButton("setFullScreenContentCallback", () -> interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        LOGGER.info("onAdClicked");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        LOGGER.info("onAdDismissedFullScreenContent");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent() {
                        LOGGER.info("onAdFailedToShowFullScreenContent");
                    }

                    @Override
                    public void onAdImpression() {
                        LOGGER.info("onAdImpression");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        LOGGER.info("onAdShowedFullScreenContent");
                    }
                })),
                createButton("show", () -> interstitialAd.show()),
                createButton("dispose", () -> interstitialAd.dispose()));
    }

    private Node createRewardedAdNode() {
        return createVBox(
                createLabel("Rewarded Ad"),
                createButton("load", () -> adsService.loadRewardedAd(RewardedAd.TEST_AD_UNIT_ID, new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad() {
                        LOGGER.info("onAdFailedToLoad");
                    }

                    @Override
                    public void onAdLoaded(RewardedAd ad) {
                        rewardedAd = ad;
                        LOGGER.info("onAdLoaded");
                    }
                })),
                createButton("setFullScreenContentCallback", () -> rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        LOGGER.info("onAdClicked");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        LOGGER.info("onAdDismissedFullScreenContent");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent() {
                        LOGGER.info("onAdFailedToShowFullScreenContent");
                    }

                    @Override
                    public void onAdImpression() {
                        LOGGER.info("onAdImpression");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        LOGGER.info("onAdShowedFullScreenContent");
                    }
                })),
                createButton("show", () -> rewardedAd.show((type, amount) -> LOGGER.info("onUserEarnedReward(" + type + ", " + amount + ")"))),
                createButton("dispose", () -> rewardedAd.dispose()));
    }

    private Node createRoot(Node... children) {
        VBox root = new VBox(children);
        ScrollPane pane = new ScrollPane(root);

        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(5);
        root.setPadding(new Insets(10));
        pane.setFitToWidth(true);

        return pane;
    }

    private VBox createVBox(Node... children) {
        VBox vBox = new VBox(children);

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);

        return vBox;
    }

    private Label createLabel(String title) {
        Label label = new Label(title);
        label.setStyle("-fx-font-size: 22px;");

        return label;
    }

    private Button createButton(String name, Runnable action) {
        Button button = new Button(name);

        button.setMaxWidth(BUTTON_WIDTH);
        button.setOnAction(ignore -> {
            LOGGER.info("button '" + name + "' was pressed");
            action.run();
        });

        return button;
    }
}
