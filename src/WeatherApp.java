import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class WeatherApp {

    private JSONObject weatherData;

    JTextField search;
    JLabel weatherConditionImage;
    JLabel temperatureText;
    JLabel temperatureDescription;
    JLabel humidityText;
    JLabel windSpeedText;



    public WeatherApp(){
        JFrame frame=new JFrame("Weather App");
        frame.setSize(400,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);




        JTextField search=new JTextField("location");
        search.setFont(new Font("Dialogue",Font.PLAIN,24));
        search.setBounds(10,10,300,40);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput=search.getText();
                changeData(userInput);
            }
        });
        frame.add(search);





         weatherConditionImage=new JLabel(loadImage("Assets/cloudy.png",150,150));
        weatherConditionImage.setBounds(100,75,150,150);
        frame.add(weatherConditionImage);


         temperatureText=new JLabel("10°C");
        temperatureText.setBounds(100,250,150,40);
        temperatureText.setFont(new Font("Dialogue",Font.BOLD,36));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(temperatureText);

         temperatureDescription=new JLabel("Cloudy");
        temperatureDescription.setBounds(100,290,150,40);
        temperatureDescription.setFont(new Font("Dialogue",Font.PLAIN,24));
        temperatureDescription.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(temperatureDescription);

        JLabel humidityImage=new JLabel(loadImage("Assets/humidity.png",43,40));
        humidityImage.setBounds(50,380,43,40);
        frame.add(humidityImage);

         humidityText=new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(93,375,50,50);
        frame.add(humidityText);


        JLabel windSpeedImage=new JLabel(loadImage("Assets/windspeed.png",43,40));
        windSpeedImage.setBounds(200,370,63,60);
        frame.add(windSpeedImage);

         windSpeedText =new JLabel("<html><b>Wind speed</b> <br> 50km/h</html>");
        windSpeedText.setBounds(260,375,120,50);
        frame.add(windSpeedText);

        JButton searchButton=new JButton(loadImage("Assets/search.png",40,40));
        searchButton.setBounds(320,10,40,40);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput=search.getText();
                changeData(userInput);
            }
        });
        frame.add(searchButton);


        frame.setVisible(true);

    }


    public ImageIcon loadImage(String resourcePath,int width,int height){
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(resourcePath));
            Image scaledImage = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public void changeData(String userInput){

        if(userInput.replaceAll("\\s", "").length()<=0){
            return;
        }
        weatherData=WeatherAppSoftware.getWeatherData(userInput);

        String weatherCondition=(String) weatherData.get("weather_condition");
        double temperature=(double)weatherData.get("temperature");
        long humidity=(long)weatherData.get("humidity");
        double windSpeed=(double) weatherData.get("windspeed");


        windSpeedText.setText("<html><b>Wind speed</b><br>"+windSpeed+"km/h</html>");
        humidityText.setText("<html><b>Humidity</b> "+humidity+"%</html>");
        temperatureDescription.setText(weatherCondition);
        temperatureText.setText(temperature+"°C");



        switch (weatherCondition){
            case "Clear":
                weatherConditionImage.setIcon(loadImage("Assets/clear.png",150,150));
                break;
            case "Cloudy":
                weatherConditionImage.setIcon(loadImage("Assets/cloudy.png",150,150));
                break;
            case "Rain":
                weatherConditionImage.setIcon(loadImage("Assets/rain.png",150,150));
                break;
            case "Snow":
                weatherConditionImage.setIcon(loadImage("Assets/snow.png",150,150));
                break;
        }

    }

    public static void main(String [] Args){
        new WeatherApp();

        //System.out.println(WeatherAppSoftware.getLocationData("Tokyo"));
        System.out.println(WeatherAppSoftware.getCurrentTime());
    }
}
