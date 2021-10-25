package mas.experimentcalculator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText input=null;
    private EditText output=null;
    private Button ac=null;
    private Button per=null;
    private Button root=null;
    private Button change=null;
    private Button delete=null;
    private Button div=null;
    private Button seven=null;
    private Button eight=null;
    private Button nine=null;
    private Button mul=null;
    private Button four=null;
    private Button five=null;
    private Button six=null;
    private Button sub=null;
    private Button one=null;
    private Button two=null;
    private Button three=null;
    private Button add=null;
    private Button zero=null;
    private Button point=null;
    private Button equal=null;


    private boolean operflag=true;//为true是运算符，false是数
    LinkedList<String> infixexp=new LinkedList<>();//中缀表达式
    LinkedList<String> postfixexp=new LinkedList<>();//后缀表达式
    private String lastoper="";//用于记录上一次运算符

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input=findViewById(R.id.input);
        output=findViewById(R.id.output);
        ac=findViewById(R.id.AC);
        per=findViewById(R.id.per);
        root=findViewById(R.id.root);
        change=findViewById(R.id.change);
        delete=findViewById(R.id.delete);
        div=findViewById(R.id.div);
        mul=findViewById(R.id.mul);
        sub=findViewById(R.id.sub);
        add=findViewById(R.id.add);
        point=findViewById(R.id.point);
        equal=findViewById(R.id.equal);

        zero=findViewById(R.id.zero);
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        three=findViewById(R.id.three);
        four=findViewById(R.id.four);
        five=findViewById(R.id.five);
        six=findViewById(R.id.six);
        seven=findViewById(R.id.seven);
        eight=findViewById(R.id.eight);
        nine=findViewById(R.id.nine);

        ac.setOnClickListener(this);
        per.setOnClickListener(this);
        root.setOnClickListener(this);
        change.setOnClickListener(this);
        delete.setOnClickListener(this);
        div.setOnClickListener(this);
        mul.setOnClickListener(this);
        sub.setOnClickListener(this);
        add.setOnClickListener(this);
        point.setOnClickListener(this);
        equal.setOnClickListener(this);

        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);

    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick (View v){
        String curtext=input.getText().toString();
        int loc = 0;//记录最后一个运算符位置

        switch(v.getId()){
            case R.id.zero:
                num(0);
                break;
            case R.id.one:
                num(1);
                break;
            case R.id.two:
                num(2);
                break;
            case R.id.three:
                num(3);
                break;
            case R.id.four:
                num(4);
                break;
            case R.id.five:
                num(5);
                break;
            case R.id.six:
                num(6);
                break;
            case R.id.seven:
                num(7);
                break;
            case R.id.eight:
                num(8);
                break;
            case R.id.nine:
                num(9);
                break;
            case R.id.point:
                dopoint(curtext,loc);
                break;
            case R.id.equal:
                doequal(curtext,loc);
                break;
            case R.id.add:
                doadd(curtext,loc);
                break;
            case R.id.sub:
                dosub(curtext,loc);
                break;
            case R.id.mul:
                domul(curtext,loc);
                break;
            case R.id.div:
                dodiv(curtext,loc);
                break;
            case R.id.delete:
                dodelete(curtext,loc);
                break;
            case R.id.root:
                doroot();
                break;
            case R.id.per:
                doper(curtext,loc);
                break;
            case R.id.AC:
                doac();
                break;
            case R.id.change:
                dochange(curtext,loc);
                break;
            default:
                break;
        }
    };

    /**
     * 输入数字
     * @param m 输入的数
     */
    @SuppressLint("SetTextI18n")
    private void num(int m){
        input.setText(input.getText()+String.valueOf(m));
        operflag=false;
    }

    /**
     * 小数点操作
     * @param curtext 输入框中的字符串
     * @param loc 记录某一符号位置
     */
    @SuppressLint("SetTextI18n")
    private void dopoint(String curtext,int loc){
        boolean dotflag = false;
        String strtemp;//用来存最后一个操作符后面的数，来限制不能输入两个小数点
        if(curtext.equals("")){//限制不能先输入小数点
            Toast.makeText(getApplicationContext(),"不能先输入小数点",Toast.LENGTH_LONG).show();
            return;
        }
        if (!lastoper.equals("")) {  //如果上一次运算符不为空
            loc = curtext.lastIndexOf(lastoper);  //得到上一次运算符在字符串中的位置
            strtemp = curtext.substring(loc + 1);      //然后截取最后一个运算符之后的字符串，为了不能输入两个小数点
        }
        else {
            strtemp = curtext;
        }
        for (int i = 0; i < strtemp.length(); i++) { //遍历字符串看看有没有小数点
            if (strtemp.charAt(i) == '.') {
                dotflag = true;
            }
        }
        if (dotflag) {  //有小数点就不能在加小数点了
            Toast.makeText(getApplicationContext(),"不能输入两个连续小数点",Toast.LENGTH_LONG).show();
        }
        else {
            input.setText(input.getText() + ".");
        }
    }

    /**
     * 等于操作
     * @param curtext 输入框中的字符串
     * @param loc 记录某一符号位置
     */
    private void doequal(String curtext,int loc){
        if(lastoper.equals("")){//输入数等号就输出该数
            output.setText(input.getText());
            return;
        }

        if((infixexp.getLast().equals("+")||infixexp.getLast().equals("-")||infixexp.getLast().equals("*")||infixexp.getLast().equals("/"))&&operflag){
            return;
        }

        if(!lastoper.equals("change")&&!lastoper.equals("%")){
            loc = curtext.lastIndexOf(lastoper);
            infixexp.add(curtext.substring(loc+1));
        }
        if(lastoper.equals("%")&&infixexp.size()==1){//用于单输9%，输出0.09，如果1+2%，直接走到188不需判断因为2%以0.02已存
            output.setText(infixexp.get(0));
        }
        if(lastoper.equals("/")){
            loc=curtext.lastIndexOf(lastoper);
            if(curtext.substring(loc+1).equals("0")){
                Toast.makeText(getApplicationContext(),"除数不能为零！",Toast.LENGTH_LONG).show();
                output.setText("错误！");
            }
        }
        toPostfix();
        double b=toValue();
        output.setText(String.valueOf(b));
        infixexp.clear();
        postfixexp.clear();
        lastoper="";
        operflag=false;
    }

    /**
     * 加法操作
     * @param curtext 输入框中的字符串
     * @param loc 记录某一符号位置
     */
    @SuppressLint("SetTextI18n")
    private void doadd(String curtext,int loc){
        if((TextUtils.isEmpty(input.getText())||operflag)){//空不能输运算符，上次输入运算符这次不能输运算符
            Toast.makeText(getApplicationContext(),"不能先输入运算符和两个运算符",Toast.LENGTH_LONG).show();
            return;
        }
        if(lastoper.equals("")){//一开始是空，按等号后还是空
            if(output.getText().toString().equals("")){//9+将9存
                infixexp.add(curtext);
            }
            else{//按下等号后的空
                infixexp.add(output.getText().toString());
                input.setText(output.getText().toString());
            }
        }
        else if(!lastoper.equals("change")&&!lastoper.equals("%")){//如果最后一个操作符是change，执行181行即可，因为在change操作已经将该数存进去了
            loc=curtext.lastIndexOf(lastoper);                     //如果最后一个操作符是%，在equal里判断
            infixexp.add(curtext.substring(loc+1));//将最后一个操作符后面的数加到链表中
        }
        operflag=true;
        input.setText(input.getText()+"+");
        infixexp.add("+");
        lastoper="+";
    }

    /**
     * 减法操作
     * @param curtext 输入框中的字符串
     * @param loc 记录某一符号位置
     */
    @SuppressLint("SetTextI18n")
    private void dosub(String curtext,int loc){
        if((TextUtils.isEmpty(input.getText())||operflag)){
            return;
        }
        if(lastoper.equals("")){
            if(output.getText().toString().equals("")){
                infixexp.add(curtext);
            }else{
                infixexp.add(output.getText().toString());
                input.setText(output.getText().toString());
            }
        }
        else if(!lastoper.equals("change")&&!lastoper.equals("%")){
            loc=curtext.lastIndexOf(lastoper);
            infixexp.add(curtext.substring(loc+1));
            System.out.println(curtext.substring(loc+1));
        }
        operflag=true;
        input.setText(input.getText()+"-");
        infixexp.add("-");
        lastoper="-";
    }

    /**
     * 乘法操作
     * @param curtext 输入框中的字符串
     * @param loc 记录某一符号位置
     */
    @SuppressLint("SetTextI18n")
    private void domul(String curtext, int loc){
        if((TextUtils.isEmpty(input.getText())||operflag)){
            return;
        }
        if(lastoper.equals("")){
            if(output.getText().toString().equals("")){
                infixexp.add(curtext);
            }else{
                infixexp.add(output.getText().toString());
                input.setText(output.getText().toString());
            }
        }
        else if(!lastoper.equals("change")&&!lastoper.equals("%")){
            loc=curtext.lastIndexOf(lastoper);
            infixexp.add(curtext.substring(loc+1));
        }
        operflag=true;
        input.setText(input.getText()+"*");
        infixexp.add("*");
        lastoper="*";
    }

    /**
     * 除法操作
     * @param curtext 输入框中的字符串
     * @param loc 记录某一符号位置
     */
    @SuppressLint("SetTextI18n")
    private void dodiv(String curtext, int loc){
        if((TextUtils.isEmpty(input.getText())||operflag)){
            return;
        }
        if(lastoper.equals("")){
            if(output.getText().toString().equals("")){
                infixexp.add(curtext);
            }else{
                infixexp.add(output.getText().toString());
                input.setText(output.getText().toString());
            }
        }
        else if(!lastoper.equals("change")&&!lastoper.equals("%")){
            loc=curtext.lastIndexOf(lastoper);
            infixexp.add(curtext.substring(loc+1));
        }

        operflag=true;
        input.setText(input.getText()+"/");
        infixexp.add("/");
        lastoper="/";
    }

    /**
     * 删除操作
     * @param curtext 输入框中的字符串
     * @param loc 记录某一符号位置
     */
    private void dodelete(String curtext,int loc){
        String clear_once_text="";//存清除一个后的字符串
        String clear_once_text_last="";//存清除一个后字符后的字符串的最后一个，可能是数也可能是操作符
        System.out.println(curtext);
        if(TextUtils.isEmpty(input.getText())){
            return;
        }
        clear_once_text=curtext.substring(0,curtext.length()-1);
        System.out.println("clear_once_text："+clear_once_text);
        clear_once_text_last=clear_once_text.substring(clear_once_text.length()-1);
        System.out.println("clear_once_text_last："+clear_once_text_last);
        if(clear_once_text_last.equals("+")||clear_once_text_last.equals("-")||clear_once_text_last.equals("*")||clear_once_text_last.equals("/")){//清到操作符了
            operflag=true;
            input.setText(clear_once_text);
            lastoper=clear_once_text_last;
        }
        else if(clear_once_text_last.equals("%")){
            return;
        }
        else {
            operflag=false;
            System.out.println("infixexp："+infixexp);
            int n=infixexp.getLast().length();
            System.out.println("n："+n);
            if(infixexp.size()>2){
                infixexp.removeLast();
                System.out.println("infixexp1："+infixexp);
                infixexp.removeLast();
                System.out.println("infixexp2："+infixexp);
                String c=clear_once_text.substring(0,clear_once_text.length()-n);
                input.setText(clear_once_text.substring(0,clear_once_text.length()-n));
                System.out.println("input："+clear_once_text.substring(0,clear_once_text.length()-n));
                System.out.println("ltlength："+infixexp.get(infixexp.size()-2).length());
            }
            else{
                //infixexp.removeLast();
                System.out.println("infixexp3："+infixexp);
                String c=infixexp.getFirst()+infixexp.getLast();
                input.setText(c);
                System.out.println("input："+clear_once_text.substring(0,clear_once_text.length()-n));
                System.out.println("ltlength："+infixexp.get(infixexp.size()-2).length());
            }

            if(infixexp.get(infixexp.size()-2).length()>1){
                lastoper=infixexp.get(infixexp.size()-1);
                System.out.println("lastoper："+lastoper);
                return;
            }
            lastoper=infixexp.get(infixexp.size()-2);
        }
    }

    /**
     * 做根号
     */
    @SuppressLint("SetTextI18n")
    private void doroot(){
        if(!operflag){
            return;
        }
        //operflag=true;
        input.setText(input.getText()+"√");
        infixexp.add("√");
        lastoper="√";
    }

    /**
     * 百分数操作
     * @param curtext 输入框中的字符串
     * @param loc 记录某一符号位置
     */
    @SuppressLint("SetTextI18n")
    private void doper(String curtext, int loc){
        double num;
        String s1="";
        if(!lastoper.equals("")){
            loc=curtext.lastIndexOf(lastoper);
            String ss=curtext.substring(loc+1);
            num=Double.parseDouble(ss);
            s1=curtext.substring(0,loc+1);
        }
        else{
            num=Double.parseDouble(curtext);
        }
        input.setText(s1+""+num+"%");
        num=num/100;
        infixexp.add(""+num);
        lastoper="%";
    }

    /**
     * 全部清空
     */
    private void doac(){
        input.setText("");
        output.setText("");
        operflag=true;
        lastoper="";
        infixexp.clear();
        postfixexp.clear();
    }

    /**
     * 正负数转换操作
     * @param curtext 输入框中的字符串
     * @param loc 记录某一符号位置
     */
    private void dochange(String curtext,int loc){
        if(TextUtils.isEmpty(input.getText())){
            return;
        }
        double a;
        String s="";//存该数前面的公式
        if(lastoper.equals("change")){//用于连续点change
            a=Double.parseDouble(infixexp.getLast());
            if(a<0){
                lastoper=infixexp.get(infixexp.size()-2);
                loc=curtext.lastIndexOf(lastoper);
                s=curtext.substring(0,loc+1);
                infixexp.removeLast();
            }
            else if(a>0){
                lastoper=infixexp.get(infixexp.size()-2);
                loc=curtext.lastIndexOf(lastoper);
                s=curtext.substring(0,loc+1);
                infixexp.removeLast();
            }
        }
        else if(!lastoper.equals("")){
            loc=curtext.lastIndexOf(lastoper);
            a=Double.parseDouble(curtext.substring(loc+1));
            s=curtext.substring(0,loc+1);//为了方便将数改成负数后合并在一起
        }
        else{
            a=Double.parseDouble(curtext);
        }
        if(a<=0){
            a = a - 2 * a;
            input.setText(s + a);
            infixexp.add("" + a);
        }
        else if(a>0){
            a = a - 2 * a;
            input.setText(s + a);
            infixexp.add("" + a);
        }
        operflag=false;
        lastoper="change";
    }

    /**
     * 比较运算符的优先级
     * @param oper1 第一个操作符
     * @param oper2 第二个操作符
     * @return 优先级
     */
    public int compare(String oper1, String oper2) {
        int i = 0,j = 0;
        switch(oper1){
            case "+":  //加的优先级是4
                i=4;
                break;
            case "-":   //减也是4
                i=4;
                break;
            case "*":  //乘是3
                i=3;
                break;
            case "/":  //除也是3
                i=3;
                break;
            case "√":  //根号是2
                i=2;
                break;
        }
        switch(oper2){
            case "+":
                j=4;
                break;
            case "-":
                j=4;
                break;
            case "*":
                j=3;
                break;
            case "/":
                j=3;
                break;
            case "√":
                i=2;
                break;
        }
        return i-j;  //优先级高的先运算   优先级1最高
    }


    /**
     * 根据运算符计算
     * @param x 第一个数
     * @param y 第二个数
     * @param oper 操作符
     * @return 计算结果
     */
    public double operate(double x,double y,String oper){ //选择运算符，计算x,y
        double value=0;
        switch(oper){
            case "+":
                value=x+y;
                break;
            case "-":
                value=x-y;
                break;
            case "*":
                value=x*y;
                break;
            case "/":
                value=x/y;
                break;
        }
        return value;  //返回运算结果
    }

    /**
     * 开发方根
     * @param x 根号里的数
     * @param oper 根号
     * @return 计算结果
     */
    public double calroot(double x,String oper){ //计算开平方根
        double value=0;
        if(x<=0){
            Toast.makeText(getApplicationContext(), "错误！负数不能开平方根",
                    Toast.LENGTH_LONG).show();
        }
        value=Math.sqrt(x);
        return value;  //返回运算结果
    }
    public boolean isNumber(String str){  //判断是不是数
        for (int i=0;i<str.length();i++){
            if (Character.isDigit(str.charAt(i))){
                return true;
            }
        }
        return false;
    }

    /**
     * 中缀表达式转后缀表达式
     */
    private void toPostfix(){  //中缀表达式转后缀表达式
        Stack<String> st=new Stack<String>();  //创建一个栈用来存运算符
        int i=0;
        System.out.println(infixexp);
        while(i<infixexp.size()){
            String ch=infixexp.get(i);  //得到中缀表达式中的字符串
            if(isNumber(ch)){    //如果是数字
                postfixexp.add(ch);  //就加到后缀表达式中
                i++;
            }else{   //如果不是数字
                while(!st.isEmpty() && compare(ch+"", st.peek())>=0){
                    postfixexp.add(st.pop());
                }
                st.push(ch+"");//如果栈顶元素优先级低，则入栈
                i++;
            }
        }
        while(!st.isEmpty()){  //如果走完了栈没空
            postfixexp.add(st.pop());  //则全部出栈
        }
    }

    /**
     * 后缀表达式求值
     * @return 计算结果
     */
    public double toValue(){  //后缀表达式求值
        Stack<Double> st = new Stack<Double>(); //用栈存数
        double value=0;
        System.out.println(postfixexp.toString());
        for(int i=0;i<postfixexp.size();i++){
            String ch=postfixexp.get(i);  //遍历后缀表达式
            if(isNumber(ch)){   //如果是数字
                st.push(Double.parseDouble(ch));   //加入到数字栈中
            }else{
                if(ch!=" "){  //跳过空格，如果不是数字，那就是运算符了
                    if(ch.equals("√")){
                        double x=st.pop();
                        value=calroot(x,ch+"");//计算运算结果
                        System.out.println((ch+"")+x+"="+value+",");//显示运算过程
                    }else{
                        double y=st.pop();  //得到栈顶的两个数字
                        double x=st.pop();
                        value=operate(x, y, ch+"");//计算运算结果
                        System.out.println(x+(ch+"")+y+"="+value+",");//显示运算过程
                    }
                    st.push(value);
                }
            }
        }
        return st.pop();  //返回运算结果
    }
}