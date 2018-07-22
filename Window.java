package RandomFood;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Container;
import java.awt.event.*;

import javax.swing.*;

public class Window extends JFrame implements ActionListener{
	
	static int index;
	
	JPanel pnl = new JPanel();
	
	JPanel result = new JPanel();
	
	JPanel buttons = new JPanel();
	
	//buttons
	JButton delete = new JButton("Delete");
	JButton add = new JButton("Add");
	JButton randomise = new JButton("Shuffle");
	JButton hide = new JButton("Hide Full List");
	JButton write = new JButton("Write");
	
	//Text areas
	JTextArea txtArea = new JTextArea(5,38);
	static JTextArea foodList = new JTextArea(8,38);
	
	//Labels
	static JLabel lbl1 = new JLabel();
	static JLabel lbl2 = new JLabel();
	
	//Container
	Container contentPane = getContentPane();
	
	static String fileName = "/food.txt";
	static List<String> food = new ArrayList<>();
	private static Random randomGenerator;
	
	public static void main(String[] args) throws IOException{
		
		Reader();
		Randomiser();
		Window gui = new Window();
		System.out.println(food);

	}
	
	public Window(){
		super("JJ to do!");
		setSize(500, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(pnl);
		setVisible(true);
		
		//add
		buttons.add(add);
		add.addActionListener(this);
		
		//delete
		buttons.add(delete);
		delete.addActionListener(this);
		
		//shuffle
		buttons.add(randomise);
		randomise.addActionListener(this);
		
		//Hide
		buttons.add(hide);
		hide.addActionListener(this);
		
		//write
		buttons.add(write);
		write.addActionListener(this);
		
		pnl.add(lbl1);
		pnl.add(txtArea);
		txtArea.setText("Type here to add new foods");
		pnl.add(foodList);
		for(String F: food){
			  foodList.append(F + "\n");
		}

		//layout
		contentPane.add("North", buttons );
	}
	
	public static void Reader() throws IOException{
		
        InputStream stream = Window.class.getClassLoader().getResourceAsStream("./food.txt");
        BufferedReader r = new BufferedReader(new InputStreamReader(stream));
//      System.out.println(stream != null); 
        String line;
        while ((line=r.readLine()) != null) {
            food.add(line);
        }
	}
	
	public static void Randomiser(){
		
		randomGenerator = new Random();
		index = randomGenerator.nextInt(food.size());
		//System.out.println(food.get(index));
		lbl1.setText("Chosen Food: " + food.get(index));
	}

	@Override
	public void actionPerformed(ActionEvent event){
		
		if (event.getSource() == delete){
			
			food.remove(index);
			System.out.println(food);
			foodList.setText(null);
			lbl1.setText("No Food Selected");
			delete.setEnabled(false);
			for(String F: food){
				  foodList.append(F + "\n");
			}
		}
		
		if (event.getSource() == add){
			food.add(txtArea.getText());
			System.out.println(food);
			foodList.setText(null);
			for(String F: food){
				  foodList.append(F + "\n");
			}
		}
		
		if (event.getSource() == randomise){
			Randomiser();
			delete.setEnabled(true);
		}
		
		if (event.getSource() == write){
			Writer();
		}
		
		if(event.getSource()==hide){
			
			String data=foodList.getText().trim();//read contents of text area into 'data'
			  if(!data.equals("")){
				  hide.setText("Show Full List");
				  foodList.setText(null);
			  }else{

				  hide.setText("Hide Full List");
				  for(String F: food){
					  foodList.append(F + "\n");
				}
			  }
			}
		}
	
	public void Writer(){
		
		try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter("food.txt");

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            for (int i = 0; i < food.size(); ){
            bufferedWriter.write(food.get(0));
            bufferedWriter.newLine();
            food.remove(0);
            }
            // Always close files.
            bufferedWriter.close();
			for(String F: food){
				  foodList.append(F + "\n");
			}
            Reader();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
	}
}
