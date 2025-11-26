use anyhow::Result;
use std::{env, fs};

fn run_prompt() -> Result<()> {
    println!("run_prompt>");
    Ok(())
}

fn run_file(script_path: &String) -> Result<()> {
    println!("Running {script_path}");
    let content = fs::read_to_string(script_path)?;
    print!("{}", content);
    Ok(())
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
