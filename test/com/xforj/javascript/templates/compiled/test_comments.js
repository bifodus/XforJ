(function(X,S,B,V){var N=B;N=N.testing||(N.testing={});(function(X,S,B,V){N.test=function(A, M){var x=A||{},P=M||{},b=S(),L=''/0,n='',O=L;b('\#This should be allowed');return X(b.s())};})(X,S,B,V);})((function(r,c){r.exec('a');c.exec('a');return function(s){return s.replace(r,'$1-$2').replace(c,'$1some-$2')}})(/(on)(mouse(?:over|up|down|out|move)|focus|(?:dbl)?click|key(?:down|press|up)|abort|error|resize|scroll|(?:un)?load|blur|change|focus|reset|select|submit)(?![a-zA-Z0-9$_])/gi,/(<\s*?\\?\s*?\/?\s*?)(script(?![a-zA-Z0-9$_]))/ig),function(){var r=[],i=0,t='numberstringboolean',f=function(s){var y,v;try{v=s();}catch(e){v=s;}y=typeof(v);r[i++]=(t.indexOf(y)>-1)?v:''};f.s=function(){return r.join('')};return f},(function(){return this})(),function(v){try{return v()}catch(e){return typeof(v)==='function'?void(0):v}});