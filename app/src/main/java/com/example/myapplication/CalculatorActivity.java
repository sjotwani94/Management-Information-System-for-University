package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity {
    TextView answer,response;
    double input1 = 0, input2 = 0;
    char[] operators = {'+','-','*','/','%'};

    boolean Addition, Subtract, Multiplication, Division, mRemainder, decimal;
    Button allClear, backSpace, modulus, division, product, minus, plus, equalTo, one, two, three, four, five, six, seven, eight, nine, zero, decimalPt, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        response=findViewById(R.id.response_box);
        answer=findViewById(R.id.answer_box);
        allClear=findViewById(R.id.all_clear);
        backSpace=findViewById(R.id.go_back);
        modulus=findViewById(R.id.modulus);
        division=findViewById(R.id.division);
        product=findViewById(R.id.multiplication);
        minus=findViewById(R.id.minus);
        plus=findViewById(R.id.plus);
        equalTo=findViewById(R.id.equal_to);
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        three=findViewById(R.id.three);
        four=findViewById(R.id.four);
        five=findViewById(R.id.five);
        six=findViewById(R.id.six);
        seven=findViewById(R.id.seven);
        eight=findViewById(R.id.eight);
        nine=findViewById(R.id.nine);
        zero=findViewById(R.id.zero);
        decimalPt=findViewById(R.id.decimal);
        exit=findViewById(R.id.toggler);
        answer.setText("");
        backSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.getText().length()!=0 && response.getText().length()!=0) {
                    response.setText(response.getText().subSequence(0, response.getText().length() - 1));
                    answer.setText(answer.getText().subSequence(0, answer.getText().length() - 1));
                }
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setText(response.getText()+"9");
                answer.setText(answer.getText()+"9");
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setText(response.getText()+"8");
                answer.setText(answer.getText()+"8");
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setText(response.getText()+"7");
                answer.setText(answer.getText()+"7");
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setText(response.getText()+"6");
                answer.setText(answer.getText()+"6");
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setText(response.getText()+"5");
                answer.setText(answer.getText()+"5");
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setText(response.getText()+"4");
                answer.setText(answer.getText()+"4");
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setText(response.getText()+"3");
                answer.setText(answer.getText()+"3");
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setText(response.getText()+"2");
                answer.setText(answer.getText()+"2");
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setText(response.getText()+"1");
                answer.setText(answer.getText()+"1");
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.getText().length() != 0 && response.getText().length() != 0) {
                    response.setText(response.getText() + "0");
                    answer.setText(answer.getText() + "0");
                }
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.getText().length() != 0 || response.getText().length() != 0) {
                    if(input1==0.0d) {
                        input1 = Float.parseFloat(answer.getText() + "");
                    }
                    boolean status = false;
                    Addition = true;
                    Subtract = false;
                    Division = false;
                    Multiplication = false;
                    mRemainder = false;
                    decimal = false;
                    answer.setText(null);
                    for (char element : operators) {
                        if (element == response.getText().charAt(response.getText().length()-1)) {
                            response.setText(response.getText().subSequence(0,response.getText().length()-1) + "+");
                            status=true;
                            break;
                        }
                    }
                    if (status==false){
                        response.setText(response.getText() + "+");
                    }
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.getText().length() != 0 || response.getText().length() != 0) {
                    if(input1==0.0d) {
                        input1 = Float.parseFloat(answer.getText() + "");
                    }
                    boolean status = false;
                    Addition = false;
                    Division = false;
                    Multiplication = false;
                    mRemainder = false;
                    Subtract = true;
                    decimal = false;
                    answer.setText(null);
                    for (char element : operators) {
                        if (element == response.getText().charAt(response.getText().length()-1)) {
                            response.setText(response.getText().subSequence(0,response.getText().length()-1) + "-");
                            status=true;
                            break;
                        }
                    }
                    if (status==false){
                        response.setText(response.getText() + "-");
                    }
                }
            }
        });

        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.getText().length() != 0 || response.getText().length() != 0) {
                    if(input1==0.0d) {
                        input1 = Float.parseFloat(answer.getText() + "");
                    }
                    boolean status = false;
                    Addition = false;
                    Subtract = false;
                    Division = false;
                    mRemainder = false;
                    Multiplication = true;
                    decimal = false;
                    answer.setText(null);
                    for (char element : operators) {
                        if (element == response.getText().charAt(response.getText().length()-1)) {
                            response.setText(response.getText().subSequence(0,response.getText().length()-1) + "*");
                            status=true;
                            break;
                        }
                    }
                    if (status==false){
                        response.setText(response.getText() + "*");
                    }
                }
            }
        });

        division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.getText().length() != 0 || response.getText().length() != 0) {
                    if(input1==0.0d) {
                        input1 = Float.parseFloat(answer.getText() + "");
                    }
                    boolean status = false;
                    Addition = false;
                    Subtract = false;
                    Multiplication = false;
                    mRemainder = false;
                    Division = true;
                    decimal = false;
                    answer.setText(null);
                    for (char element : operators) {
                        if (element == response.getText().charAt(response.getText().length()-1)) {
                            response.setText(response.getText().subSequence(0,response.getText().length()-1) + "/");
                            status=true;
                            break;
                        }
                    }
                    if (status==false){
                        response.setText(response.getText() + "/");
                    }
                }
            }
        });

        modulus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.getText().length() != 0 || response.getText().length() != 0) {
                    if(input1==0.0d) {
                        input1 = Float.parseFloat(answer.getText() + "");
                    }
                    boolean status = false;
                    Addition = false;
                    Subtract = false;
                    Division = false;
                    Multiplication = false;
                    mRemainder = true;
                    decimal = false;
                    answer.setText(null);
                    for (char element : operators) {
                        if (element == response.getText().charAt(response.getText().length()-1)) {
                            response.setText(response.getText().subSequence(0,response.getText().length()-1) + "%");
                            status=true;
                            break;
                        }
                    }
                    if (status==false){
                        response.setText(response.getText() + "%");
                    }
                }
            }
        });

        equalTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Addition || Subtract || Multiplication || Division || mRemainder) {
                    input2 = Float.parseFloat(answer.getText() + "");
                }

                if (Addition) {

                    answer.setText(input1 + input2 + "");
                    input1 = input1 + input2;
                    Addition = false;
                }

                if (Subtract) {

                    answer.setText(input1 - input2 + "");
                    input1 = input1 - input2;
                    Subtract = false;
                }

                if (Multiplication) {
                    answer.setText(input1 * input2 + "");
                    input1 = input1 * input2;
                    Multiplication = false;
                }

                if (Division) {
                    answer.setText(input1 / input2 + "");
                    input1 = input1 / input2;
                    Division = false;
                }
                if (mRemainder) {
                    answer.setText(input1 % input2 + "");
                    input1 = input1 % input2;
                    mRemainder = false;
                }
            }
        });

        allClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setText("");
                response.setText("");
                input1 = 0.0;
                input2 = 0.0;
            }
        });

        decimalPt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (decimal) {
                    //do nothing or you can show the error
                } else {
                    answer.setText(answer.getText() + ".");
                    response.setText(response.getText() + ".");
                    decimal = true;
                }
            }
        });
    }
}
