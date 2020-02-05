package simulation;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class Main extends Application
{

    private double anchorX;
    private double anchorY;
    private double anchorAngleX = 0;
    private double anchorAngelY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        final int CIRCLE_CENTER_X = 300 ;
        final int CIRCLE_CENTER_Y = 300 ;

        final Duration MONTH = Duration.seconds(2);
        final Duration YEAR = MONTH.multiply(12);






        Sphere sun = createSun();

        PointLight sunLight = new PointLight();
        sunLight.translateXProperty().add(sun.getTranslateX());
        sunLight.translateYProperty().add(sun.getTranslateY());
        sunLight.translateZProperty().add(-900);

        AmbientLight ambientLight = new AmbientLight();





        //********** mercury ************
        Sphere mercury = createMercury();
        Circle mercuryPath = new Circle();
        mercuryPath.setRadius(sun.getBoundsInLocal().getWidth() + 100);
        PathTransition transitionMercury = new PathTransition();
        setTransitionPath(transitionMercury, mercury, mercuryPath, 5);


        //********** venus ************
        Sphere venus = createVenus();
        Circle venusPath = new Circle();
        venusPath.setRadius(sun.getBoundsInLocal().getWidth() + 800);
        PathTransition transitionVenus = new PathTransition();
        setTransitionPath(transitionVenus, venus, venusPath, 6);


        //********** earth and moon ************
        Sphere earth = createEarth();
        Circle earthPath = new Circle();
        earthPath.setRadius(sun.getBoundsInLocal().getWidth() + 3000);
        PathTransition transitionEarth = new PathTransition();
        setTransitionPath(transitionEarth, earth, earthPath, 9);


        Sphere moon = createMoon();
        Circle moonPath = new Circle();
        moonPath.setRadius(earth.getBoundsInLocal().getWidth() + 60);
        PathTransition transitionMoon = new PathTransition();
        setTransitionPath(transitionMoon, moon, moonPath, 2);


        //********** mars ************
        Sphere mars = createMars();
        Circle marsPath = new Circle();
        marsPath.setRadius(sun.getBoundsInLocal().getWidth() + 5000);
        PathTransition transitionMars = new PathTransition();
        setTransitionPath(transitionMars, mars, marsPath, 11);


        //********** jupiter ************
        Sphere jupiter = createJupiter();
        Circle jupiterPath = new Circle();
        jupiterPath.setRadius(sun.getBoundsInLocal().getWidth() + 8000);
        PathTransition transitionJupiter = new PathTransition();
        setTransitionPath(transitionJupiter, jupiter, jupiterPath, 12);


        //********** saturn ************
        Sphere saturn = createSaturn();
        Circle saturnPath = new Circle();
        saturnPath.setRadius(sun.getBoundsInLocal().getWidth() + 12000);
        PathTransition transitionSaturn = new PathTransition();
        setTransitionPath(transitionSaturn, saturn, saturnPath, 16);


        //********** uranus ************
        Sphere uranus = createUranus();
        Circle uranusPath = new Circle();
        uranusPath.setRadius(sun.getBoundsInLocal().getWidth() + 16000);
        PathTransition transitionUranus = new PathTransition();
        setTransitionPath(transitionUranus, uranus, uranusPath, 19);


        //********** neptune ************
        Sphere neptune = createNeptune();
        Circle neptunePath = new Circle();
        neptunePath.setRadius(sun.getBoundsInLocal().getWidth() + 18000);
        PathTransition transitionNeptune = new PathTransition();
        setTransitionPath(transitionNeptune, neptune, neptunePath, 22);









        StackPane root = new StackPane();
        StackPane moonPane = new StackPane();

        moonPane.translateXProperty().bind(earth.translateXProperty());
        moonPane.translateYProperty().bind(earth.translateYProperty());
        moonPane.setMaxSize(100, 100);
        //moonPane.setStyle("-fx-background-color: #EAEAAE;");
        moonPane.getChildren().add(moon);



        root.getChildren().add(sun);
        root.getChildren().add(sunLight);
        root.getChildren().add(ambientLight);




        root.getChildren().add(moonPane);
        root.getChildren().add(earthPath);
        root.getChildren().add(earth);
        root.getChildren().add(mercuryPath);
        root.getChildren().add(mercury);
        root.getChildren().add(venusPath);
        root.getChildren().add(venus);
        root.getChildren().add(marsPath);
        root.getChildren().add(mars);
        root.getChildren().add(jupiterPath);
        root.getChildren().add(jupiter);
        root.getChildren().add(saturnPath);
        root.getChildren().add(saturn);
        root.getChildren().add(uranusPath);
        root.getChildren().add(uranus);
        root.getChildren().add(neptunePath);
        root.getChildren().add(neptune);















        Scene scene = new Scene(root, 1500, 1500, true);
        scene.setFill(Color.BLACK);

        Camera camera = new PerspectiveCamera();
        camera.setTranslateX(-200);
        camera.setTranslateY(-200);
        camera.setTranslateZ(-40000);
        camera.setNearClip(1);
        camera.setFarClip(100000);

        scene.setCamera(camera);


        initMouseControl(root, scene, primaryStage);


        // keyboard listener - key event (use javafx keyEvent)
        // MOVES THE OBJECT RELATIVE TO A FIXED CAMERA POSITION
        // press w and s to move object closer or father away from camera for Z axis
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event ->
        {
            switch(event.getCode())
            {
                case W:
                    camera.translateZProperty().set(camera.getTranslateZ() + 800);
                    break;

                case S:
                    camera.translateZProperty().set(camera.getTranslateZ() - 800);
                    break;

            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }






    private void setTransitionPath(PathTransition transitionPlanet, Sphere planet, Circle planetPath, int seconds)
    {
        transitionPlanet.setPath(planetPath);
        transitionPlanet.setNode(planet);
        transitionPlanet.setInterpolator(Interpolator.LINEAR);
        transitionPlanet.setDuration(Duration.seconds(seconds));
        transitionPlanet.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        transitionPlanet.setCycleCount(PathTransition.INDEFINITE);
        planetPath.setVisible(false);
        transitionPlanet.play();

    }



    private Sphere createSun()
    {
        Sphere sun = new Sphere(1000);
        PhongMaterial sunMaterial = new PhongMaterial();
        sunMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/sun.jpg")));
        sun.setMaterial(sunMaterial);

        return sun;
    }

    private Sphere createMercury()
    {
        Sphere mercury = new Sphere(25);
        PhongMaterial mercuryMaterial = new PhongMaterial();
        mercuryMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/mercury.jpg")));

        return mercury;
    }

    private Sphere createVenus()
    {
        Sphere venus = new Sphere(35);
        PhongMaterial venusMaterial = new PhongMaterial();
        venusMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/venus.jpg")));
        venus.setMaterial(venusMaterial);

        return venus;
    }

    private Sphere createEarth()
    {
        Sphere earth = new Sphere(50);
        PhongMaterial earthMaterial = new PhongMaterial();
        earthMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/earth.jpg")));
        earth.setMaterial(earthMaterial);

        return earth;
    }

    private Sphere createMoon()
    {
        Sphere moon = new Sphere(10);
        PhongMaterial moonMaterial = new PhongMaterial();
        moonMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/moon.jpg")));
        moon.setMaterial(moonMaterial);

        return moon;
    }

    private Sphere createMars()
    {
        Sphere mars = new Sphere(45);
        PhongMaterial marsMaterial = new PhongMaterial();
        marsMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/mars.jpg")));
        mars.setMaterial(marsMaterial);

        return mars;
    }

    private Sphere createJupiter()
    {
        Sphere jupiter = new Sphere(200);
        PhongMaterial jupiterMaterial = new PhongMaterial();
        jupiterMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/jupiter.jpg")));
        jupiter.setMaterial(jupiterMaterial);

        return jupiter;
    }

    private Sphere createSaturn()
    {
        Sphere saturn = new Sphere(150);
        PhongMaterial saturnMaterial = new PhongMaterial();
        saturnMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/saturn.png")));
        saturn.setMaterial(saturnMaterial);

        return saturn;
    }

    private Sphere createUranus()
    {
        Sphere uranus = new Sphere(100);
        PhongMaterial uranusMaterial = new PhongMaterial();
        uranusMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/uranus.jpg")));
        uranus.setMaterial(uranusMaterial);

        return uranus;
    }

    private Sphere createNeptune()
    {
        Sphere neptune = new Sphere(100);
        PhongMaterial neptuneMaterial = new PhongMaterial();
        neptuneMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/neptune.jpg")));
        neptune.setMaterial(neptuneMaterial);

        return neptune;
    }

    private void initMouseControl(StackPane root, Scene scene, Stage stage)
    {
        Rotate xRotate;
        Rotate yRotate;

        root.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );

        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        // track mouse movement
        scene.setOnMousePressed(mouseEvent ->
        {
            anchorX = mouseEvent.getSceneX();
            anchorY = mouseEvent.getSceneY();

            anchorAngleX = angleX.get();
            anchorAngelY = angleY.get();
        });

        scene.setOnMouseDragged(mouseEvent ->
        {
            angleX.set(anchorAngleX - (anchorY - mouseEvent.getSceneY()));

            angleY.set(anchorAngelY + anchorX - mouseEvent.getSceneX());
        });

        stage.addEventHandler(ScrollEvent.SCROLL, scrollEvent ->
        {
            double delta = scrollEvent.getDeltaY();

            root.translateZProperty().set(root.getTranslateZ() + delta);
        });
    }

}


