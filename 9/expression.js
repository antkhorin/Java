function cnst(value) {
	return function() {
		return value;
	}
}

function variable(name) {
	return function() {
		if (name == "x") {
			return arguments[0];
		} else if (name == "y") {
			return arguments[1];
		} else {
			return arguments[2];
		}
	}
}

function abstractBinaryFunction(func, expr1, expr2) {
	return function() {
		return eval("expr1.apply(null, arguments)" + func + "expr2.apply(null, arguments)");
	}
}

function add(expr1, expr2) {
	return abstractBinaryFunction('+', expr1, expr2);
}

function subtract(expr1, expr2) {
	return abstractBinaryFunction('-', expr1, expr2);
}

function multiply(expr1, expr2) {
	return abstractBinaryFunction('*', expr1, expr2);
}

function divide(expr1, expr2) {
	return abstractBinaryFunction('/', expr1, expr2);
}

function mod(expr1, expr2) {
	return abstractBinaryFunction('%', expr1, expr2);
}

function power(expr1, expr2) {
	return function() {
		return Math.pow(expr1.apply(null, arguments), expr2.apply(null, arguments));
	}
}

function negate(expr) {
	return function() {
		return -expr.apply(null, arguments);
	}
}

function abs(expr) {
	return function() {
		return Math.abs(expr.apply(null, arguments));
	}
}

function log(expr) {
	return function() {
		return Math.log(expr.apply(null, arguments));
	}
}

function parse(string) {
	var number = ''
	var i = 0;
	var m = [];
	while (i < string.length) {
		var a;
		if (string[i] >= '0' && string[i] <= '9' || string[i] == '-' && string[i + 1] >= '0' && string[i + 1] <= '9') {
			while (string[i] >= '0' && string[i] <= '9' || string[i] == '-') {
				number = number + string[i++];
			}
			m.push(cnst(parseInt(number)));
			number = '';
		} else if (string[i] >= 'x' && string[i] <= 'z') {
			m.push(variable(string[i]));
			i++;
		} else if (string.substr(i, 6) == 'negate' || string.substr(i, 3) == 'abs' || string.substr(i, 3) == 'log'){
			if (string.substr(i, 6) == 'negate') {
				m.push(negate(m.pop()));
				i += 3;
			} else if (string.substr(i, 3) == 'abs') {
				m.push(abs(m.pop()));
			} else {
				m.push(log(m.pop()));
			}
			i += 3;
		} else if (string.substr(i, 2) == '**'){
			a = m.pop();
			m.push(power(m.pop(), a));
			i += 2;
		} else if (string[i] != ' '){
			a = m.pop();
			m.push(abstractBinaryFunction(string[i], m.pop(), a));
			i++;
		} else {
			i++;
		}
	}
	return m.pop();
}