import java.util.*;
import java.util.Arrays; 
class Carrd{
    private int face;
    private String suit;    
    public Carrd(int face, String suit) {
        super();
        this.face = face;
        this.suit = suit;
    }   
    public int getFace() {
        return face;
    }  
    public void setFace(int face) {
        this.face = face;
    }  
    public String getSuit() {
        return suit;
    }   
    public void setSuit(String suit) {
        this.suit = suit;
    }   
    public String toString() {
        String faceString = face == 14 ? "Ace":
                (face == 11 ? "Jack":
                    (face == 12 ? "Queen":
                        (face == 13 ? "King": String.valueOf(face))));
        return faceString + " of " + suit;
    }
}
class Deck{
    private final int faces[] = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 , 12, 13, 14};
    private final String suits[]={"Hearts","Diamonds","Clubs","Spades"};
    private Carrd deck[];
    private final int TOTAL_CARDS=52;
    private Random randNum; 
    public Deck(){        
        deck = new Carrd[TOTAL_CARDS];
        randNum = new Random();
        for(int i=0;i<deck.length;i++){
            deck[i] = new Carrd(faces[i%13],suits[i/13]);
        }
    }   
    public void shuffle(){
        for(int i=0;i<deck.length;i++){
            int j = randNum.nextInt(TOTAL_CARDS);
            Carrd c = deck[i];
            deck[i] = deck[j];
            deck[j] = c;
        }       
    }  
    public Carrd getCard(int index){
        return deck[index];        
    }   
}
class Player{
    public final static int MAX_CARD = 3;
    private Carrd cards[];   
    public Player() {
        cards = new Carrd[MAX_CARD];
    }
    public Carrd[] getCards() {
        return cards;
    }
    public void setCardAtIndex(Carrd c, int index) {        
        if(index >= 0 && index < MAX_CARD)           
            cards[index] = c;             
    }   
    public Carrd[] sequence() {
        Carrd temp;
        for (int i = 0; i < cards.length; i++) {
           for(int j = i ; j > 0 ; j--){
                if(cards[j].getFace() < cards[j-1].getFace()){
                    temp = cards[j];
                    cards[j] = cards[j-1];
                    cards[j-1] = temp;
                }
            }
        }   
        return cards; 
    } 
}
class Play{
    private Player[] players;
    private Deck deck;
    public Play() {
        deck = new Deck();
        int p; 
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter no.of players");
        p = sc.nextInt();
        players = new Player[p];       
        for (int i =0; i< p ; i++){
            players[i] = new Player();
        }
        deck.shuffle();
    }
    public void dealCards() {
        int count = 0;
        for (int i = 0; i < players[0].getCards().length; i++){
            for (int j = 0; j < players.length; j++){
                players[j].setCardAtIndex(deck.getCard(count++), i);
            }
        }        
    }
    public void showCards() {
        int checkHighest = 0;
        int [] sum = new int[players.length];            
        for (int i = 0; i < players.length; i++) {           
            System.out.print("Player" + (i + 1) + " : ");
            Carrd[] scard = players[i].sequence();
            for(int k =0; k<scard.length; k++){
                    System.out.print("{" + scard[k] + "}");
                }
            System.out.println("\n");
            if((scard[0].getFace() == 2) && (scard[1].getFace() == 3) && (scard[2].getFace() == 14)){
                    sum[i] = 38;
                    checkHighest = sum[i];
                }
            else if(((scard[2].getFace() - scard[0].getFace())==2) && (scard[2].getFace() - scard[1].getFace()==1)){
                    sum[i] =0 ;
                    for(int j =0; j<scard.length ; j++){
                        sum[i] += scard[j].getFace();
                    }
                    if(checkHighest < sum[i])
                        checkHighest = sum[i];
                }            
        }
        if(checkHighest!= 0){
            for(int i =0; i<players.length; i++){
                if(checkHighest == sum[i]){
                    System.out.println("Player " + (i+1) + " is the winner.");
                }                
            }
        } else{
            System.out.println("No sequence found!!!");
        }
    }
}
public class CardGame {
    public static void main(String[] args) {        
        Play g = new Play();
        g.dealCards();        
        g.showCards();
    }
}
