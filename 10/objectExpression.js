function Const(value) {
    this.value = value;
	this.evaluate = function() {
		return this.value;
	}
	this.toString = function() {
		return this.value + '';
	}
	this.diff = function(variable) {
		return new Const(0);
	}
	this.simplify = function() {
		return this;
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
	this.toString = function() {
		return this.name;
	}
	this.diff = function(variable) {
		if (variable == this.name) {
			return new Const(1);
		} else {
			return new Const(0);
		}
	}
	this.simplify = function() {
		return this;
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
	toString: function() {
		return this.expr1.toString() + ' ' + this.expr2.toString() + ' ' + this.op;
	},
	check: function() {
		this.expr1 = this.expr1.simplify();
		this.expr2 = this.expr2.simplify();
	},
	checkConst: function() {
		if (this.expr1.value != undefined && this.expr2.value != undefined) {
			return new Const(this.evaluate());
		}
		return this;
	}
}

function Add(expr1, expr2) {
	this.constructor(expr1, expr2, "+");
	this.diff = function(variable) {
		return new Add(this.expr1.diff(variable), this.expr2.diff(variable));
	}
	this.simplify = function() {
		this.check();
		if (this.expr1.value == 0) {
			return this.expr2;
		}
		if (this.expr2.value == 0) {
			return this.expr1;
		}
		return this.checkConst();
	}
}

function Subtract(expr1, expr2) {
	this.constructor(expr1, expr2, "-");
	this.diff = function(variable) {
		return new Subtract(this.expr1.diff(variable), this.expr2.diff(variable));
	}
	this.simplify = function() {
		this.check();
		if (this.expr1.value == 0) {
			return new Negate(this.expr2).simplify();
		}
		if (this.expr2.value == 0) {
			return this.expr1;
		}
		return this.checkConst();
	}
}

function Multiply(expr1, expr2) {
	this.constructor(expr1, expr2, "*");
	this.diff = function(variable) {
		return new Add(new Multiply(this.expr1.diff(variable), this.expr2), new Multiply(this.expr1, this.expr2.diff(variable)));
	}
	this.simplify = function() {
		this.check();
		if (this.expr1.value == 0 || this.expr2.value == 0) {
			return new Const(0);
		} if (this.expr1.value == 1) {
			return this.expr2;
		} if (this.expr2.value == 1) {
			return this.expr1;
		}
		return this.checkConst();
	}
}

function Divide(expr1, expr2) {
	this.constructor(expr1, expr2, "/");
	this.diff = function(variable) {
		return new Divide(new Subtract(new Multiply(this.expr1.diff(variable), this.expr2), new Multiply(this.expr1, this.expr2.diff(variable))), new Multiply(this.expr2, this.expr2));
	}
	this.simplify = function() {
		this.check();
		if (this.expr1.value == 0) {
			return new Const(0);
		}
		if (this.expr2.value == 1) {
			return this.expr1;
		}
		return this.checkConst();
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
	toString: function() {
		return this.expr.toString() + ' ' + this.op;
	},
	check: function() {
		this.expr = this.expr.simplify();
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
	this.simplify = function() {
		this.check();
		if (this.expr.value != undefined) {
			return new Const(-this.expr.evaluate());
		} if (this.expr.op == "negate") {
			return this.expr.expr;
		}
		return this;
	}
}

function Sin(expr) {
	this.expr = expr;
	this.op = "sin";
	this.diff = function(variable) {
		return new Multiply(new Cos(this.expr), this.expr.diff(variable));
	}
	this.simplify = function() {
		this.check();
		if (this.expr.value != undefined) {
			return new Const(Math.sin(this.expr.evaluate()));
		} 
		return this;
	}
}

function Cos(expr) {
	this.expr = expr;
	this.op = "cos";
	this.diff = function(variable) {
		return new Multiply(new Negate(new Sin(this.expr)), this.expr.diff(variable));
	}
	this.simplify = function() {
		this.check();
		if (this.expr.value != undefined) {
			return new Const(Math.cos(this.expr.evaluate()));
		} 
		return this;
	}
}

Sin.prototype = AbstractUnaryOperation;
Cos.prototype = AbstractUnaryOperation;
Negate.prototype = AbstractUnaryOperation;

function parse(string) {
	var number = ''
	var i = 0;
	var m = [];
	var a;
	var b;
	while (i < string.length) {
		if (string[i] >= '0' && string[i] <= '9' || string[i] == '-' && string[i + 1] >= '0' && string[i + 1] <= '9') {
			while (string[i] >= '0' && string[i] <= '9' || string[i] == '-') {
				number = number + string[i++];
			}
			m.push(new Const(parseInt(number)));
			number = '';
		} else if (string[i] >= 'x' && string[i] <= 'z') {
			m.push(new Variable(string[i]));
			i++;
		} else if (string.substr(i, 6) == 'negate'){
			m.push(new Negate(m.pop()));
			i += 6;
		} else if (string.substr(i, 3) == 'sin'){
			m.push(new Sin(m.pop()));
			i += 3;
		} else if (string.substr(i, 3) == 'cos'){
			m.push(new Cos(m.pop()));
			i += 3;
		} else if (string[i] != ' ') {
			a = m.pop();
			b = m.pop();
			switch (string[i]) {
				case '+': {
					a = new Add(b, a);
					break;
				}
				case '-': {
					a = new Subtract(b, a);
					break;
				}
				case '*': {
					a = new Multiply(b, a);
					break;
				}
				case '/': {
					a = new Divide(b, a);
				}
			}
			m.push(a);
			i++;
		} else {
			i++;
		}
	}
	return m.pop();
}