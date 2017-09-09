function Const(value) {
    this.value = value;
	this.evaluate = function() {
		return this.value;
	}
	this.diff = function(variable) {
		return new Const(0);
	}
	this.prefix = function() {
		return this.value + "";
	}
}

function Variable(name) {
    this.name = name;
	this.evaluate = function() {
		if (this.name == 'x') {
			return arguments[0];
		} else if (this.name == 'y') {
			return arguments[1];
		} else {
			return arguments[2];
		}
	}
	this.diff = function(variable) {
		if (variable == this.name) {
			return new Const(1);
		} else {
			return new Const(0);
		}
	}
	this.prefix = function() {
		return this.name;
	}
}

var AbstractBinaryOperation = {
	constructor: function(expr1, expr2, op) {
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.op = op;
	},
	evaluate: function() {
		return eval("this.expr1.evaluate.apply(this.expr1, arguments)" + this.op + "this.expr2.evaluate.apply(this.expr2, arguments)")
	},
	prefix: function() {
		return "(" + this.op + " " + this.expr1.prefix() + " " + this.expr2.prefix() + ")";
	}
}

function Add(expr1, expr2) {
	this.constructor(expr1, expr2, "+");
	this.diff = function(variable) {
		return new Add(this.expr1.diff(variable), this.expr2.diff(variable));
	}
}

function Subtract(expr1, expr2) {
	this.constructor(expr1, expr2, "-");
	this.diff = function(variable) {
		return new Subtract(this.expr1.diff(variable), this.expr2.diff(variable));
	}
}

function Multiply(expr1, expr2) {
	this.constructor(expr1, expr2, "*");
	this.diff = function(variable) {
		return new Add(new Multiply(this.expr1.diff(variable), this.expr2), new Multiply(this.expr1, this.expr2.diff(variable)));
	}
}

function Divide(expr1, expr2) {
	this.constructor(expr1, expr2, "/");
	this.diff = function(variable) {
		return new Divide(new Subtract(new Multiply(this.expr1.diff(variable), this.expr2), new Multiply(this.expr1, this.expr2.diff(variable))), new Multiply(this.expr2, this.expr2));
	}
}

Add.prototype = AbstractBinaryOperation;
Subtract.prototype = AbstractBinaryOperation;
Multiply.prototype = AbstractBinaryOperation;
Divide.prototype = AbstractBinaryOperation;

var AbstractUnaryOperation = {
	evaluate: function() {
		return eval("Math." + this.op + "(this.expr.evaluate.apply(this.expr, arguments))")
	},
	prefix: function() {
		return "(" + this.op + " " + this.expr.prefix() + ")";
	}
}

function Negate(expr) {
	this.expr = expr;
	this.op = "negate";
	this.evaluate = function() {
		return -this.expr.evaluate.apply(this.expr, arguments);
	}
	this.diff = function(variable) {
		return new Negate(this.expr.diff(variable));
	}
}

function Sin(expr) {
	this.expr = expr;
	this.op = "sin";
	this.diff = function(variable) {
		return new Multiply(new Cos(this.expr), this.expr.diff(variable));
	}
}

function Cos(expr) {
	this.expr = expr;
	this.op = "cos";
	this.diff = function(variable) {
		return new Multiply(new Negate(new Sin(this.expr)), this.expr.diff(variable));
	}
}

function Exp(expr) {
	this.expr = expr;
	this.op = "exp";
	this.diff = function(variable) {
		return new Multiply(this.expr.diff(variable), this);
	}
}

function ArcTan(expr) {
	this.expr = expr;
	this.op = "atan";
	this.diff = function(variable) {
		return new Multiply(new Divide(new Const(1), new Add(new Const(1), new Multiply(this.expr, this.expr))), this.expr.diff(variable));
	}
}

Sin.prototype = AbstractUnaryOperation;
Cos.prototype = AbstractUnaryOperation;
Exp.prototype = AbstractUnaryOperation;
ArcTan.prototype = AbstractUnaryOperation;
Negate.prototype = AbstractUnaryOperation;

function parsePrefix(string) {
	string = string.replace(/ +/g, " ");
	string = string.replace(/\( +/g, "(");
	string = string.replace(/ +\)/g, ")");
	string = string.replace(/ *\(/g, " (");
	string = string.replace(/\) */g, ") ").trim();
	if (string == "") throw "Empty input"
	var k = 0;
	var c = 0;
	while (k < string.length) {
		if (string[k] == '(') c++;
		if (string[k] == ')') c--;
		if (c < 0) throw "Missing )"
		k++;
	}
	if (c != 0) throw "Missing )"
	if (string[0] == '(') {
		var a;
        var b;
        var i = 4;
		if (string.substr(1, 6) == 'negate') {
			i = 9;
			if (string.length < 9) throw "Invalid unary 0 operations";
		} else if (string.substr(1, 3) == "sin" || string.substr(1, 3) == "cos" || string.substr(1, 3) == "exp") {
			i = 6;
			if (string.length < 6) throw "Invalid unary 0 operations";
		} else if (string.substr(1, 4) == "atan") {
			i = 7;
			if (string.length < 7) throw "Invalid unary 0 operations";
		} else if (string[1] != '+' && string[1] != '-' && string[1] != '*' && string[1] != '/') throw "Unknown operation"
		if (string[i - 2] != ' ') throw "Invalid binary 0 operations";
		if (string[string.length - 1] != ')') throw "Excessive info"
		if (string[i - 1] == '(') {
            var c = 1;
            while (c > 0 && i < string.length) {
                if (string[i] == '(') {
                    c++;
                } else if (string[i] == ')') {
                    c--;
                }
                i++;
            }
            if (c > 0) throw "Invalid binary 1 operation";
        } else {
            if (string[i - 1] == '-' && string[i] >= '0' && string[i] <= '9'|| string[i - 1] >= '0' && string[i - 1] <= '9') {
                while (i < string.length && string[i] >= '0' && string[i] <= '9') i++;
            } else if (string[i - 1] < 'x' || string[i - 1] > 'z') throw "Invalid binary 1 operation";
        }
		if (string[1] == 'n') {
			a = parsePrefix(string.substring(8, string.length - 1));
		} else if (string[1] == 's' || string[1] == 'c' || string[1] == 'e') {
			a = parsePrefix(string.substring(5, string.length - 1));
		} else if (string[1] == 'a') {
			a = parsePrefix(string.substring(6, string.length - 1));
		} else {
			if (string[i] != ' ') throw "Invalid binary 1 operation";
			a = parsePrefix(string.substring(3, i));
			b = parsePrefix(string.substring(i + 1, string.length - 1));
		}
		switch (string[1]) {
			case '+':
			{
				return new Add(a, b);
			}
			case '-':
			{
                return new Subtract(a, b);
			}
			case '*':
			{
                return new Multiply(a, b);
			}
			case '/':
			{
				return new Divide(a, b);
			}
			case 'n':
			{
				return new Negate(a);
			}
			case 'a':
			{
				return new ArcTan(a);
			}
			case 's':
			{
				return new Sin(a);
			}
			case 'c':
			{
				return new Cos(a);
			}
			case 'e':
			{
				return new Exp(a);
			}
		}
	} else {
        var i = 1;
        if (string[0] == '-' && string[1] >= '0' && string[1] <= '9'|| string[0] >= '0' && string[0] <= '9') {
            while (i < string.length && string[i] >= '0' && string[i] <= '9') i++;
            if (i != string.length) throw "To many operand";
            return new Const(parseInt(string));
        } else if (string.length == 1 && !(string[0] >= 'x' && string[0] <= 'z')) throw "Unknown variable"
		else if (!(string[0] >= 'x' && string[0] <= 'z')) throw "Invalid number";
        if (string.length != 1) throw "Too many operations";
        return new Variable(string);
    }
}