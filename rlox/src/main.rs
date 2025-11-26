use anyhow::Result;
use std::io::Write;
use std::{env, fmt, fs, io};

struct LRet {
    had_error: bool,
}

impl LRet {
    fn new(had_error: bool) -> Self {
        Self { had_error }
    }
}

impl fmt::Display for LRet {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        write!(f, "LRet({})", self.had_error)
    }
}

fn report(line: i32, where_info: String, msg: String) -> Result<()> {
    eprintln!("[line {line}] Error {where_info}: {msg}");
    Ok(())
}

fn error(line: i32, msg: String) -> Result<()> {
    report(line, "".to_string(), msg)
}

fn run(source: String) -> Result<LRet> {
    print!("{}", source);
    Ok(LRet::new(false))
}

fn run_prompt() -> Result<()> {
    loop {
        print!("> ");
        io::stdout().flush()?;
        let mut line = String::new();
        io::stdin().read_line(&mut line)?;
        if line.is_empty() {
            break;
        }
        run(line)?;
    }
    Ok(())
}

fn run_file(script_path: &String) -> Result<()> {
    println!("Processing {script_path}");
    let ret = run(fs::read_to_string(script_path)?);
    match ret {
        Ok(result) => {
            println!("Run file with ret {result}");
            Ok(())
        }
        Err(e) => Err(e),
    }
}

fn main() {
    let args: Vec<String> = env::args().collect();
    if args.len() > 2 {
        eprintln!("Usage: rlox [script]");
        return;
    }
    let res = match args.get(1) {
        Some(script_file) => run_file(script_file),
        None => run_prompt(),
    };

    if let Err(e) = res {
        eprintln!("Exit with error: {}", e);
    }
}
